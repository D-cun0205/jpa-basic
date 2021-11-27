package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.item.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("A")
@Entity
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
