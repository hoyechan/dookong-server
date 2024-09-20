package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("SELECT SUM(p.pointValue) FROM Point p WHERE p.member.id = :memberId AND MONTH(p.date) = MONTH(CURRENT_DATE) AND YEAR(p.date) = YEAR(CURRENT_DATE)")
    Integer findMonthlyPointsByMemberId(@Param("memberId") Long memberId);
}
