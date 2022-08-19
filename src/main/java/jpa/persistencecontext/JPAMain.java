package jpa.persistencecontext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAMain {

    public static void main(String[] args) {
        // 어플리케이션 로딩 시점에 딱 1개만 생성되야 함
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        try {
            Member member = new Member();
            member.setId(2L);
            member.setName("dcun");

            // 조회
            entityManager.find(Member.class, 1L);

            // 등록
            entityManager.persist(member);

            // 수정
            member.setName("sanghun");

            // 삭제
            entityManager.remove(member);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
