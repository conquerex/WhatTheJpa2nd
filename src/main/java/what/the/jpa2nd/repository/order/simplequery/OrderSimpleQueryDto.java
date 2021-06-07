package what.the.jpa2nd.repository.order.simplequery;

import lombok.Data;
import what.the.jpa2nd.domain.Address;
import what.the.jpa2nd.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name; // LAZY 초기화. 없으면 영속성 컨텍스트에 없으면 쿼리를 통해 찾아온다.
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address; // LAZY 초기화
    }
}
