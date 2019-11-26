package food.delivery.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author szendihorvathjozsef
 */
@Data
@Entity
@Table(name = "menu")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer percent;

    @OneToOne
    @JoinColumn(name = "type_name", referencedColumnName = "name")
    private ItemType type;
}
