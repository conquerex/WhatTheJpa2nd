package what.the.jpa2nd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import what.the.jpa2nd.domain.item.Book;
import what.the.jpa2nd.domain.item.Item;
import what.the.jpa2nd.repository.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book param) {
        // 영속성 엔티티
        Item findItem = itemRepository.findOne(itemId); // 조회하고

        // 변경하는 옳은 방법
        // findItem.change(...); // 직접 변경

        // 이것도 직접 변경
        findItem.setPrice(param.getPrice());
        findItem.setStockQuantity(param.getStockQuantity());

        // 아래를 수행할 필요가 없다.
        // findItem는 영속성 엔티티기 때문 (변경감지)
        // itemRepository.save(findItem);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
