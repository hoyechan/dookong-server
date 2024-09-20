package com.example.itrieone.repository;

import com.example.itrieone.domain.RecycleBin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecycleBinRepository extends JpaRepository<RecycleBin, Long> {
    // 필요한 추가 쿼리 메서드 정의
}