package jpa.persistencecontext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DetectChange {
    public static void main(String[] args) {
        EntityManager em = StaticEntityManagerFactory.staticEMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = em.find(Member.class, 1L);
            member.setName("changeName");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        StaticEntityManagerFactory.staticEMF.close();
    }
}
