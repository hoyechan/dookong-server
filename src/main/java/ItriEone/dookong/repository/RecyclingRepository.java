package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Recycling;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RecyclingRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Long save(Recycling recycling){
        em.persist(recycling);
        return recycling.getId();
    }

    public Recycling findOne(Long id){
        return em.find(Recycling.class, id);
    }
}
