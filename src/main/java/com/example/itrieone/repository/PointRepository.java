package com.example.itrieone.repository;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Point;
import com.example.itrieone.domain.Recycling;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("SELECT SUM(p.pointValue) FROM Point p WHERE p.member.id = :memberId AND MONTH(p.date) = MONTH(CURRENT_DATE) AND YEAR(p.date) = YEAR(CURRENT_DATE)")
    Integer findMonthlyPointsByMemberId(@Param("memberId") Long memberId);

    // 특정 회원의 전체 포인트 내역 조회
    List<Point> findByMemberId(Long memberId);
}
