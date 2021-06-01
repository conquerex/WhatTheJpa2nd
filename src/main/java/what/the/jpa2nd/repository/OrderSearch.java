package what.the.jpa2nd.repository;

import lombok.Getter;
import lombok.Setter;
import what.the.jpa2nd.domain.OrderStatus;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
