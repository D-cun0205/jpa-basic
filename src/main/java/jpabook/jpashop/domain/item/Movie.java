package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.item.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("M")
@Entity
@Getter
@Setter
public class Movie extends Item {

    private String author;
    private String actor;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
