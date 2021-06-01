package what.the.jpa2nd.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import what.the.jpa2nd.domain.Address;
import what.the.jpa2nd.domain.Member;
import what.the.jpa2nd.domain.Order;
import what.the.jpa2nd.domain.OrderStatus;
import what.the.jpa2nd.domain.item.Book;
import what.the.jpa2nd.domain.item.Item;
import what.the.jpa2nd.exception.NotEnoughStockException;
import what.the.jpa2nd.repository.OrderRepository;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("BBBBook", 10000, 20);

        int orderCount = 4;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상태 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("상품 종류 수", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격", 10000 * orderCount, getOrder.getTotalPrice());
        Assert.assertEquals("재고가 줄어야", (20 - 4), item.getStockQuantity());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("NNNameee");
        member.setAddress(new Address("cityyy", "strrrreeet", "zippppp"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("BBBBook", 10000, 20);
        int orderCount = 5;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 취소 상태", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("그만큼 재고가 증가", 20, item.getStockQuantity());

    }


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("BBBBook", 10000, 20);
        int orderCount = 33;

        // when
        orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Assert.fail("재고 수량 부족 예외가 발생해야 한다.");

    }

}