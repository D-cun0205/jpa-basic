package jpa.persistencecontext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static jpa.persistencecontext.StaticEntityManagerFactory.staticEMF;

public class WriteLazySupportTransaction {
    public static void main(String[] args) {
        EntityManager em = staticEMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member memberA = new Member(4L, "CU");
            Member memberB = new Member(5L, "SEVEN");

            em.persist(memberA);
            em.persist(memberB);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        staticEMF.close();
    }
}
