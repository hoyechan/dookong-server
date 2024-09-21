package com.example.itrieone.service;

import com.example.itrieone.domain.*;
import com.example.itrieone.dto.item.ItemCreateRequestDto;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.recycling.RecyclingReadDto;
import com.example.itrieone.dto.recycling.RecyclingSubmitRequestDto;
import com.example.itrieone.repository.MemberRepository;
import com.example.itrieone.repository.PointRepository;
import com.example.itrieone.repository.RecyclingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
    public RecyclingReadDto submitRecycling(RecyclingSubmitRequestDto recyclingSubmitRequestDto, MultipartFile beforeImage, MultipartFile afterImage) throws IOException{

        try{
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            // uploads 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // 폴더가 없으면 생성
            }

            // 이미지 파일 저장
            String beforeImageFileName = UUID.randomUUID().toString() + "_" + beforeImage.getOriginalFilename();
            Path beforeImageFilePath = uploadPath.resolve(beforeImageFileName);
            Files.copy(beforeImage.getInputStream(), beforeImageFilePath, StandardCopyOption.REPLACE_EXISTING);

            String afterImageFileName = UUID.randomUUID().toString() + "_" + afterImage.getOriginalFilename();
            Path afterImageFilePath = uploadPath.resolve(afterImageFileName);
            Files.copy(afterImage.getInputStream(), afterImageFilePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 저장 후 URL 생성
            String beforeImageUrl = "/uploads/" + beforeImageFileName;
            String afterImageUrl = "/uploads/" + afterImageFileName;

            // 생성된 이미지 URL을 recyclingSubmitRequestDto에 설정
            Recycling recycling = recyclingSubmitRequestDto.toEntity();
            recycling.setBeforePictureUrl(beforeImageUrl);
            recycling.setAfterPictureUrl(afterImageUrl);

            //연관관계 설정
            Member member = memberRepository.findById(recyclingSubmitRequestDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            member.addRecycling(recycling);

            // Recycling 객체 생성 및 저장
            Recycling savedRecycling = recyclingRepository.save(recycling);

            return RecyclingReadDto.fromEntity(savedRecycling);
        }catch(Exception e){
            System.err.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw e;  // 예외를 다시 던져 트랜잭션 롤백 확인
        }
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
