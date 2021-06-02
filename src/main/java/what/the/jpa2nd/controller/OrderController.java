package what.the.jpa2nd.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import what.the.jpa2nd.domain.Member;
import what.the.jpa2nd.domain.Order;
import what.the.jpa2nd.domain.item.Item;
import what.the.jpa2nd.repository.OrderSearch;
import what.the.jpa2nd.service.ItemService;
import what.the.jpa2nd.service.MemberService;
import what.the.jpa2nd.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // 식별자만 넘긴다. 서비스에서 영속상태에서 진행할 수 있도록.
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }


    // 아래와 같은 결과를 얻을 수 있다
    // http://localhost:8080/orders?memberName=NNName&orderStatus=ORDER
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model) {
        // @ModelAttribute
        // model.addAttribute을 자동으로 수행

        // 단순한 조회라면 서비스를 거치지 않고 바로 repository에서 불러도 된다.
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}
