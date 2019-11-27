package food.delivery.repositories;

import food.delivery.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author szendihorvath
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByUser(Long user);

}
