package what.the.jpa2nd.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import what.the.jpa2nd.domain.item.Item;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    /**
     * 생성 메서드
     */
/*
    protected OrderItem() {
        // 필드의 setter로 OrderItem 생성을 막기 위함
        // 이 방법이 유지 보수에 더 편함 (필드 수정 등등)
        // 동일한 방법 : @NoArgsConstructor(access = AccessLevel.PROTECTED)
    }
*/
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }


    /**
     * 비즈니스 로직
     */
    public void cancel() {
        getItem().addStock(count); // 재고 수량 원복
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
