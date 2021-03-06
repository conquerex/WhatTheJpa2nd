package what.the.jpa2nd.domain.item;

import lombok.Getter;
import lombok.Setter;
import what.the.jpa2nd.domain.Category;
import what.the.jpa2nd.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 싱글 테이블 전략
 */
@Entity
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 비즈니스 로직
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("Need more stock!!");
        }
        this.stockQuantity = restStock;
    }
}
