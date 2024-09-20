package com.example.itrieone.service;

import com.example.itrieone.domain.RecycleBin;
import com.example.itrieone.repository.RecycleBinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecycleBinService {
    private final RecycleBinRepository recycleBinRepository;

    // 모든 분리수거통의 위치를 가져오기
    public List<RecycleBin> getAllRecycleBins() {
        return recycleBinRepository.findAll();
    }

    // 기타 필요한 메서드...
}
