package ItriEone.dookong.service;

import ItriEone.dookong.domain.Member;
import ItriEone.dookong.domain.Point;
import ItriEone.dookong.domain.Recycling;
import ItriEone.dookong.domain.RecyclingStatus;
import ItriEone.dookong.repository.MemberRepository;
import ItriEone.dookong.repository.PointRepository;
import ItriEone.dookong.repository.RecyclingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RecyclingService {

    private final MemberRepository memberRepository;
    private final RecyclingRepository recyclingRepository;
    private final PointRepository pointRepository;

    /**
     * 분리수거 활동 등록
     * @param memberId
     * @param mediaUrl
     * @return
     */
    public Long submitRecycling(Long memberId, String mediaUrl) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Recycling recycling = new Recycling();
        recycling.setMediaUrl(mediaUrl);
        recycling.setRecyclingStatus(RecyclingStatus.PENDING);

        // 연관 관계 설정
        member.addRecycling(recycling);
        recyclingRepository.save(recycling);

        return recycling.getId();
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
        recycling.setPoint(point);

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
}
