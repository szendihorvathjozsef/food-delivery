package food.delivery.repositories;

import food.delivery.entities.ItemAllergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvath
 */
@Repository
public interface ItemAllergenRepository extends JpaRepository<ItemAllergen, Long> {
}
