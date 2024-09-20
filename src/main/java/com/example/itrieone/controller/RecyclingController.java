package com.example.itrieone.controller;

import com.example.itrieone.dto.recycling.RecyclingReadDto;
import com.example.itrieone.dto.recycling.RecyclingSubmitRequestDto;
import com.example.itrieone.service.RecyclingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recycling")
public class RecyclingController {

    private final RecyclingService recyclingService;

    /**
     * 분리수거 등록 API
     * @param dto 분리수거 등록 요청 DTO
     * @return 등록된 분리수거 내역 DTO
     */
    @PostMapping("/submit")
    public ResponseEntity<RecyclingReadDto> submitRecycling(@RequestBody RecyclingSubmitRequestDto dto) {
        RecyclingReadDto recyclingReadDto = recyclingService.submitRecycling(dto);
        return ResponseEntity.ok(recyclingReadDto);
    }

    /**
     * 분리수거 활동 승인 API
     * @param recyclingId 승인할 분리수거 ID
     * @return 승인 결과 메시지
     */
    @PostMapping("/approve/{recyclingId}")
    public ResponseEntity<String> approveRecycling(@PathVariable Long recyclingId) {
        recyclingService.approveRecycling(recyclingId);
        return ResponseEntity.ok("분리수거 활동이 승인되었습니다.");
    }

    /**
     * 분리수거 활동 거절 API
     * @param recyclingId 거절할 분리수거 ID
     * @return 거절 결과 메시지
     */
    @PostMapping("/reject/{recyclingId}")
    public ResponseEntity<String> rejectRecycling(@PathVariable Long recyclingId) {
        recyclingService.rejectRecycling(recyclingId);
        return ResponseEntity.ok("분리수거 활동이 거절되었습니다.");
    }

//    // 특정 사용자의 분리수거 활동 조회
//    @GetMapping("/{memberId}")
//    public ResponseEntity<List<RecyclingReadDto>> getRecyclingsByMemberId(@PathVariable Long memberId) {
//        List<RecyclingReadDto> recyclingList = recyclingService.getRecyclingsByMemberId(memberId);
//        return ResponseEntity.ok(recyclingList);
//    }

    // 모든 분리수거 활동 조회
    @GetMapping("/all")
    public ResponseEntity<List<RecyclingReadDto>> getAllRecyclings() {
        List<RecyclingReadDto> recyclingList = recyclingService.getAllRecyclings();
        return ResponseEntity.ok(recyclingList);
    }
}
