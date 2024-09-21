package com.example.itrieone.repository;

import com.example.itrieone.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByusername(String username);

    Optional<Member> findByEmail(String email);

    // ADMIN 역할을 가진 멤버는 제외하고 totalPoint로 내림차순 정렬
    @Query("SELECT m FROM Member m WHERE m.role <> 'ADMIN' ORDER BY m.totalPoint DESC")
    List<Member> findMonthlyRanking();
}

