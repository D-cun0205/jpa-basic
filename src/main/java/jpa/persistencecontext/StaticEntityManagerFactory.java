package jpa.persistencecontext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StaticEntityManagerFactory {
    public static EntityManagerFactory staticEMF = Persistence.createEntityManagerFactory("hello");
}
