package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@Getter
@Setter
/**
 * 네임드 엔티티 그래프 : 엔티티 조회할 때 연관된 엔티티도 같이 조회
 * name : 엔티티 그래프 이름, attributeNodes : 함께 조회할 속성 선택과 @NamedAttributeNode 어노테이션 사용
 * Order 엔티티에서 조회 할 memeber 엔티티는 패치가 LAZY로 설정되어 있지만 네임드 엔티티 그래프 설정으로 인해 같이 조회
 * 네임드 엔티티안에 속성을 여러개 설정 하고 싶을 때 @NamedEntityGraphs 사용

@NamedEntityGraph(
        name = "Order.withMember",
        attributeNodes = { @NamedAttributeNode("member") }
)
 */

/**
 * 영속성 컨텍스트에 관리중인 엔티티를 사용하여 네임드 엔티티 그래프를 사용하는 경우 영속성에서 관리되는 엔티티 자체를 반납하므로
 * 영속성에서 관리되기전 호출할때 네임드 엔티티 그래프를 사용하여 연관된 엔티티들을 같이 조회 되게 해야한다
 * hints put 값에 설정되는 fetchgraph, loadgraph의 차이, fetch : 속성값에 대한 엔티티만 조회, load : 해당 엔티티의 FetchType.EAGER를 찾아서 같이 조회
 * subgraph : 해당 엔티티에서 접근하지 않은 엔티티를 호출되는 엔티티에서 접근 가능할 경우
 * subgraph를 사용한 네임드 엔티티 그래프
 * 엔티티 그래프 조회 : Order -> Member, Order -> OrderItem, OrderItem -> Item 조회
 */
@NamedEntityGraph(
        name = "Order.withAll",
        attributeNodes = {
                @NamedAttributeNode("member"),
                @NamedAttributeNode(value = "orderItems", subgraph = "orderItems")
        },
        subgraphs = @NamedSubgraph(name = "orderItems", attributeNodes = {
                @NamedAttributeNode("item")
        })
)
public class Order {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setMember(Member member) {
        this.member = member;
        member.getOrdersList().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(new Date());
        return order;
    }

    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.CAMP)
            throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.");

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
