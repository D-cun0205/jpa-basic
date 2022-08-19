package jpa.entitymapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private long id;

    @Column(unique = true)
    private String name;
}
