package food.delivery.repositories;

import food.delivery.entities.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author szend
 */
@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {
}
