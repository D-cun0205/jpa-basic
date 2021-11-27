package jpabook.jpashop.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import jpabook.jpashop.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepositoryImpl extends QueryDslRepositorySupport implements CustomOrderRepository {

    @PersistenceContext
    EntityManager em;

    public OrderRepositoryImpl() {
        super(Order.class); //상속받은 부모클래스에 엔티티 클래스 정보를 전달
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery query = from(order);

        //OrderSearch에서 가져온 유저명이 스트링타입으로 데이터가 들어왔는지 체크
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            //leftjoin에서 첫번째 파라미터 : 엔티티를 시작점으로 접근 경로, 두번째 파라미터 : 접근 대상 엔티티
            query.leftJoin(order.member, member)
                    //조인하여 조건절로 사용자 명 = OrderSearch.getMemberName으로 받은 사용자 명
                    .where(member.username.contains(orderSearch.getMemberName()));
        }

        //OrderSearch에서 가져온 주문상태값이 비어있지 않으면
        if (orderSearch.getOrderStatus() != null) {
            //엔티티 데이터들 중 가져온 상태값과 동일한 로우 조회
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        //sql 실행 및 결과 반환
        return query.list(order);
    }
}
