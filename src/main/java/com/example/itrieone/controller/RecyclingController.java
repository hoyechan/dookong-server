package com.example.itrieone.controller;

import com.example.itrieone.config.MultipartConfig;
import com.example.itrieone.dto.item.ItemCreateRequestDto;
import com.example.itrieone.dto.item.ItemReadDto;
import com.example.itrieone.dto.recycling.RecyclingReadDto;
import com.example.itrieone.dto.recycling.RecyclingSubmitRequestDto;
import com.example.itrieone.service.RecyclingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recycling")
public class RecyclingController {

    private final RecyclingService recyclingService;

    /**
     * 분리수거 등록 API
     * @param recyclingData
     * @param beforeImage
     * @param afterImage
     * @return
     * @throws IOException
     */
    @PostMapping("/submit")
    public ResponseEntity<RecyclingReadDto> submitRecycling(
            @RequestPart("recyclingData") String recyclingData,
            @RequestPart("beforeImage")MultipartFile beforeImage,
            @RequestPart("afterImage")MultipartFile afterImage) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        RecyclingSubmitRequestDto recyclingSubmitRequestDto = objectMapper.readValue(recyclingData, RecyclingSubmitRequestDto.class);

        RecyclingReadDto recyclingReadDto = recyclingService.submitRecycling(recyclingSubmitRequestDto, beforeImage, afterImage);
        return ResponseEntity.ok(recyclingReadDto);
    }

    /**
     * 분리수거 활동 승인 API
     * @param recyclingId 승인할 분리수거 ID
     * @return 승인 결과 메시지
     */
    @PostMapping("/approve/{recyclingId}")
    public ResponseEntity<String> approveRecycling(@PathVariable("recyclingId") Long recyclingId) {
        recyclingService.approveRecycling(recyclingId);
        return ResponseEntity.ok("분리수거 활동이 승인되었습니다.");
    }

    /**
     * 분리수거 활동 거절 API
     * @param recyclingId 거절할 분리수거 ID
     * @return 거절 결과 메시지
     */
    @PostMapping("/reject/{recyclingId}")
    public ResponseEntity<String> rejectRecycling(@PathVariable("recyclingId") Long recyclingId) {
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
