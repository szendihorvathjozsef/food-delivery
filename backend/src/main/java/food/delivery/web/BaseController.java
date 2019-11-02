package food.delivery.web;

import food.delivery.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    protected UserService userService;
}