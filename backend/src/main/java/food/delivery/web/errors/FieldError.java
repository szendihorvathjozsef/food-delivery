package food.delivery.web.errors;

import lombok.Data;

import java.io.Serializable;

/**
 * @author szendihorvath
 */
@Data
public class FieldError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;
}
