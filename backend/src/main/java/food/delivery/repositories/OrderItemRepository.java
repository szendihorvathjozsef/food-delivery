package food.delivery.repositories;

import food.delivery.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvath
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
