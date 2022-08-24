package jpa;

import jpa.embeddedtype.Address;
import jpa.embeddedtype.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class AssociationTest {

    public static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hello");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("sanghun");
            member.setHomeAddress(new Address("a", "b", "c"));

            member.getFavoriteFoods().add("A");
            member.getFavoriteFoods().add("B");

            member.getAddressHistory().add(new Address("d", "e", "f"));
            member.getAddressHistory().add(new Address("g", "h", "i"));

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            for (Address address : findMember.getAddressHistory()) {
                System.out.println(address.getCity());
            }

            for (String favoriteFood : findMember.getFavoriteFoods()) {
                System.out.println(favoriteFood);
            }

            //잘못된 방법
            //findMember.getHomdeAddress().setCity("new City");

            //아래와 같은 방법 사용
            Address home = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", home.getStreet(), home.getZipcode()));

            findMember.getFavoriteFoods().remove("A");
            findMember.getFavoriteFoods().add("C");

            //아래와 같은 방법을 사용하면 1개 삭제, 1개 추가가 진행될 것 같지만
            //member_id 에 해당하는
            findMember.getAddressHistory().remove(new Address("d", "e", "f"));
            findMember.getAddressHistory().add(new Address("j", "k", "n"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
