package food.delivery.entities;

import food.delivery.util.enums.CouponStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author szendihorvathjozsef
 */
@Data
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private CouponStatus status = CouponStatus.UNUSED;

    @OneToOne
    @JoinColumn(name = "coupon_type_id", referencedColumnName = "id")
    private CouponType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
