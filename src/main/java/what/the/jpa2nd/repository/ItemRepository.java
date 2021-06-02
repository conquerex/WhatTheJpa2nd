package what.the.jpa2nd.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import what.the.jpa2nd.domain.item.Item;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // itemId가 있네?
            // merge 수행. but item값을 전부 변경. 세팅되지 않은 필드는 null로 update
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i ", Item.class)
                .getResultList();
    }

}
