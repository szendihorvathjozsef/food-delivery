package food.delivery.repositories;

import food.delivery.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * @author szendihorvath
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT SUM(ot.quantity) as quantity, it.name as name FROM Order o " +
            "LEFT JOIN OrderItem ot ON ot.order = o.id " +
            "LEFT JOIN Item it ON it.id = ot.item " +
            "WHERE o.start >= :start AND o.end <= :end " +
            "GROUP BY it.name")
    List<DailyStatistics> findAllByStartIsAfterAndEndIsBefore(@Param("start") Instant start, @Param("end") Instant end);
}
