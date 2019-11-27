package food.delivery.util.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserStatus {
    ACTIVE,
    NEED_ACTIVATION,
    BANNED,
}
