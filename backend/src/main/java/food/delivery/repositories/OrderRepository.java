package food.delivery.repositories;

import food.delivery.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szendihorvath
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
