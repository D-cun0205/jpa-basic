package jpa.persistencecontext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static jpa.persistencecontext.StaticEntityManagerFactory.staticEMF;

public class SelectFromCache {
    public static void main(String[] args) {
        EntityManager em =
                staticEMF.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member firstFindMember = em.find(Member.class, 1L);
            Member secondFindMember = em.find(Member.class, 1L);
            System.out.println(firstFindMember == secondFindMember);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        staticEMF.close();
    }
}
