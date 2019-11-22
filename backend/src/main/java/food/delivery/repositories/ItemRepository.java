package food.delivery.repositories;

import food.delivery.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvath
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
