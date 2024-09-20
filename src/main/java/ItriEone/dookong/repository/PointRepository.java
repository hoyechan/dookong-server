package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Point;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PointRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Long save(Point point){
        em.persist(point);
        return point.getId();
    }

    public Point findOne(Long id){
        return em.find(Point.class, id);
    }
}
