package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specifications.where;
import static jpabook.jpashop.domain.OrderSpec.memberNameLike;
import static jpabook.jpashop.domain.OrderSpec.orderStatusEq;

@Getter
@Setter
public class OrderSearch {

    private String memberName; //회원이름
    private OrderStatus orderStatus; //주문상태

    public Specification<Order> toSpecification() {
        return where(memberNameLike(memberName))
                .and(orderStatusEq(orderStatus));
    }
}
