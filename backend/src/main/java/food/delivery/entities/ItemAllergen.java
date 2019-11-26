package food.delivery.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "allergen")
public class ItemAllergen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 2, max = 50)
    @Column(length = 50, nullable = false, unique = true)
    private String name;

}
