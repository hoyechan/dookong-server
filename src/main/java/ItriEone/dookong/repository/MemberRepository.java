package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByusername(String username);

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m LEFT JOIN m.points p ON FUNCTION('MONTH', p.date) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', p.date) = FUNCTION('YEAR', CURRENT_DATE) GROUP BY m.id ORDER BY COALESCE(SUM(p.pointValue), 0) DESC")
    List<Member> findMonthlyRanking();


}
