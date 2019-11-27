package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min = 4, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private Integer percent;

    @OneToOne
    @JoinColumn(name = "type_name", referencedColumnName = "name")
    private ItemType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("coupons")
    private User user;
}
