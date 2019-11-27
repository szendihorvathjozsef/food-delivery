package food.delivery.web;

import food.delivery.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class BaseController {

    @Value("${app.name}")
    protected String applicationName;

}