package food.delivery.repositories;

import food.delivery.entities.Coupon;
import food.delivery.util.enums.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author szendihorvath
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByUser_IdAndStatus(Long id, CouponStatus status);
    List<Coupon> findAllByUser_LoginAndStatus(String login, CouponStatus status);

}
