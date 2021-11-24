package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;

@DiscriminatorValue("M")
@Getter
@Setter
public class Movie {

    private String author;
    private String actor;
}
