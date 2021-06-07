package what.the.jpa2nd.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import what.the.jpa2nd.domain.Address;
import what.the.jpa2nd.domain.Order;
import what.the.jpa2nd.domain.OrderStatus;
import what.the.jpa2nd.repository.OrderRepository;
import what.the.jpa2nd.repository.OrderSearch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne) 관계 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        // Order --> Member --> Order.. 무한루프
        // 양방향 연관관계가 걸리는 곳에 모두 JsonIgnore를 넣어줘야 한다
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            // order.getMember()는 Proxy 객체
            // BUT.. order.getMember().getName() : 강제 초기화
            // OrderItems는 초기화가 안되었기에 null로 출력
            order.getMember().getName();
            order.getMember().getAddress();
        }

        // 엔티티를 API로 노출하는 방식. 매우 안좋음.
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        // N + 1 문제
        // 1(order 결과 2개) + N(order 내부에 있는 member와 delivery 쿼리)
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDeilvery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화. 없으면 영속성 컨텍스트에 없으면 쿼리를 통해 찾아온다.
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
