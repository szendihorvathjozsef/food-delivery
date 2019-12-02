package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_item")
@EqualsAndHashCode(exclude = {"order"})
@ToString(exclude = {"order"})
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("orders")
    private Order order;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "item_id")
    private Item item;

}
