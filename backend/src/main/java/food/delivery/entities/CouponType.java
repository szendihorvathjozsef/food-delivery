package food.delivery.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author szend
 */
@Data
@Entity
@Table(name = "coupon_type")
public class CouponType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private Integer percent;

}
