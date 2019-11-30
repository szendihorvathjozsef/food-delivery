package food.delivery.repositories;

import food.delivery.entities.Order;
import food.delivery.util.enums.OrderStatus;
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

    @Query("SELECT new food.delivery.repositories.DailyStatistics(SUM(ot.quantity), it.name) FROM Order o " +
            "LEFT JOIN OrderItem ot ON ot.order = o.id " +
            "LEFT JOIN Item it ON it.id = ot.item " +
            "WHERE o.start >= :start AND o.end <= :end AND o.status = 'FINISHED' " +
            "GROUP BY it.name")
    List<DailyStatistics> findAllByStartIsAfterAndEndIsBefore(@Param("start") Instant start, @Param("end") Instant end);

    List<Order> findAllByCreatedOnIsAfter(Instant timestamp);

    List<Order> findAllByStatusIsNot(OrderStatus orderStatus);

    List<Order> findAllByUser_Login(String login);

    List<Order> findAllByUser_LoginAndStatusIsNot(String login, OrderStatus orderStatus);
}
