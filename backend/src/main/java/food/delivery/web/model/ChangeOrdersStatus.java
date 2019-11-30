package food.delivery.web.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author szend
 */
@Data
public class ChangeOrdersStatus {

    @NotNull
    private List<Long> ids;

}
