package food.delivery.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserStatus {
    ACTIVATED,
    NEED_ACTIVATION,
    BANNED,
}
