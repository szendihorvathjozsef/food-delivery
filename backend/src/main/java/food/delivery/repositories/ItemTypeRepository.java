package food.delivery.repositories;

import food.delivery.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvath
 */
@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
}
