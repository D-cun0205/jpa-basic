package jpa.entitymapping;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(Gen)
    private long id;

    @Column(unique = true, length = 100, updatable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private AccountEnum accountEnum;

    @Temporal(TemporalType.TIME)
    private String time;

    @Temporal(TemporalType.DATE)
    private String date;

    @Temporal(TemporalType.TIMESTAMP)
    private String timestamp;

    @Lob
    private String stringLob;

    @Transient
    private String temp;

}