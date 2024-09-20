package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Long save(Item item){
        em.persist(item);
        return item.getId();
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
}
