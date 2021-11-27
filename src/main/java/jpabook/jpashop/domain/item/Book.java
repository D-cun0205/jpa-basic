package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.item.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("B")
@Entity
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
