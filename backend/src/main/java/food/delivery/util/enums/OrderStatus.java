package food.delivery.util.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author szendihorvath
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OrderStatus {
   ORDERED, IN_PROGRESS, FINISHED
}
