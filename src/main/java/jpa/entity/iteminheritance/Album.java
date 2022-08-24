package jpa.entity.iteminheritance;

import jpa.entity.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;
}
