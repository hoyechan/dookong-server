package com.example.itrieone.controller;

import com.example.itrieone.domain.RecycleBin;
import com.example.itrieone.dto.recyclebin.RecycleBinReadDto;
import com.example.itrieone.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;

    // 분리수거통 위치를 반환하는 엔드포인트
    @GetMapping("/api/recycle-bins")
    public List<RecycleBinReadDto> getAllRecycleBins() {
        List<RecycleBin> recycleBins = recycleBinService.getAllRecycleBins();

        // RecycleBin 엔티티를 RecycleBinReadDto로 변환하여 반환
        return recycleBins.stream()
                .map(RecycleBinReadDto::fromEntity)
                .collect(Collectors.toList());
    }
}
