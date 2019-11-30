package food.delivery.web;

import org.springframework.beans.factory.annotation.Value;

public class BaseController {

    @Value("${app.name}")
    protected String applicationName;

}