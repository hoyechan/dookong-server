package com.example.itrieone.service;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Point;
import com.example.itrieone.domain.Recycling;
import com.example.itrieone.domain.RecyclingStatus;
import com.example.itrieone.dto.recycling.RecyclingReadDto;
import com.example.itrieone.dto.recycling.RecyclingSubmitRequestDto;
import com.example.itrieone.repository.MemberRepository;
import com.example.itrieone.repository.PointRepository;
import com.example.itrieone.repository.RecyclingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecyclingService {

    private final MemberRepository memberRepository;
    private final RecyclingRepository recyclingRepository;
    private final PointRepository pointRepository;

    /**
     * 분리수거 등록
     * @param recyclingSubmitRequestDto
     * @return
     */
    public RecyclingReadDto submitRecycling(RecyclingSubmitRequestDto recyclingSubmitRequestDto) {
        Member member = memberRepository.findById(recyclingSubmitRequestDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Recycling recycling = recyclingSubmitRequestDto.toEntity();
        member.addRecycling(recycling);
        Recycling returnRecycling = recyclingRepository.save(recycling);

        return RecyclingReadDto.fromEntity(returnRecycling);
    }

    /**
     * 분리수거 활동 승인(ADMIN만 가능)
     * @param recyclingId
     */
    @Transactional
    public void approveRecycling(Long recyclingId) {
        Recycling recycling = recyclingRepository.findById(recyclingId).orElseThrow(() -> new IllegalArgumentException("분리수거 내역을 찾을 수 없습니다."));
        if (recycling.getRecyclingStatus() != RecyclingStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 분리수거 내역입니다.");
        }

        // 승인 처리
        recycling.setRecyclingStatus(RecyclingStatus.APPROVED);

        // 포인트 증가
        Member member = recycling.getMember();

        Point point = new Point();
        point.setDate(LocalDateTime.now());
        point.setDescription("분리수거 활동");
        point.setPointValue(10);

        int originTotalPoint = member.getTotalPoint();
        member.setTotalPoint(originTotalPoint + point.getPointValue());

        //연관 관계 설정
        member.addPoint(point);

        pointRepository.save(point);
    }

    /**
     * 분리수거 활동 거절(ADMIN만 가능)
     * @param recyclingId
     */
    @Transactional
    public void rejectRecycling(Long recyclingId) {
        Recycling recycling = recyclingRepository.findById(recyclingId).orElseThrow(() -> new IllegalArgumentException("분리수거 내역을 찾을 수 없습니다."));
        if (recycling.getRecyclingStatus() != RecyclingStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 분리수거 내역입니다.");
        }

        // 거절 처리
        recycling.setRecyclingStatus(RecyclingStatus.REJECTED);
    }

    /**
     * 모든 분리수거 활동 조회 (관리자용)
     * @return List<RecyclingReadDto>
     */
    public List<RecyclingReadDto> getAllRecyclings() {
        List<Recycling> recyclings = recyclingRepository.findAll();
        return recyclings.stream()
                .map(RecyclingReadDto::fromEntity)
                .collect(Collectors.toList());
    }

}
