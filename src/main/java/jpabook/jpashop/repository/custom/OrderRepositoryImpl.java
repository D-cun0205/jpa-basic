package jpabook.jpashop.repository.custom;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.List;

public class OrderRepositoryImpl extends QueryDslRepositorySupport implements CustomOrderRepository {

    public OrderRepositoryImpl() {
        super(Order.class); //상속받은 부모클래스에 엔티티 클래스 정보를 전달
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        return null;
    }
}
