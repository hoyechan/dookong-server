package ItriEone.dookong.repository;

import ItriEone.dookong.domain.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MessageRepository {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Long save(Message message){
        em.persist(message);
        return message.getId();
    }

    public Message findOne(Long id){
        return em.find(Message.class, id);
    }
}
