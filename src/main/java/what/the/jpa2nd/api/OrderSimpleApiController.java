package what.the.jpa2nd.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import what.the.jpa2nd.domain.Order;
import what.the.jpa2nd.repository.OrderRepository;
import what.the.jpa2nd.repository.OrderSearch;

import java.util.List;

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

    @GetMapping("/api/v1/sample-orders")
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
}
