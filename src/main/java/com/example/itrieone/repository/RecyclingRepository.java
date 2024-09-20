package com.example.itrieone.repository;

import com.example.itrieone.domain.Member;
import com.example.itrieone.domain.Point;
import com.example.itrieone.domain.Recycling;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecyclingRepository extends JpaRepository<Recycling, Long> {
}
