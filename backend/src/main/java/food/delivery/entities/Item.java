package food.delivery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Float price;

    @Column(name = "kcal")
    private Integer kCal;

    @Column(name = "protein")
    private Integer protein;

    @Column(name = "fat")
    private Integer fat;

    @Column(name = "carbs")
    private Integer carbs;

    @Size(max = 255)
    @Column(name = "image_name", length = 255)
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_name")
    private ItemType itemType;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "items_allergen",
            joinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "allergen_name", referencedColumnName = "name")}
    )
    @BatchSize(size = 20)
    private Set<ItemAllergen> allergens = new HashSet<>();

}
