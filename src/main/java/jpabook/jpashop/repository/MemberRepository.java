package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery(
                "SELECT m FROM Member m", Member.class
        ).getResultList();
    }

    public List<Member> findByName(String username) {
        return em.createQuery(
         "SELECT m FROM Member m WHERE m.username = :username", Member.class
        ).setParameter("username", username).getResultList();
    }
}