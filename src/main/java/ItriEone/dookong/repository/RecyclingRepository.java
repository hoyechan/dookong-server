package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Recycling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecyclingRepository extends JpaRepository<Recycling, Long> {
}