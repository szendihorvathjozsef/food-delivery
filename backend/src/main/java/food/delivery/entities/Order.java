package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author szendihorvathjozsef
 */
@Data
@Entity
@Table(name = "order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_cost")
    private Double totalCost;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties("order")
    private Set<OrderItem> orders = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("order")
    private User user;

}
