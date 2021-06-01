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
            // item이 있네?
            // update와 비슷한 것. 추후 본 주석 수정 예정
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
