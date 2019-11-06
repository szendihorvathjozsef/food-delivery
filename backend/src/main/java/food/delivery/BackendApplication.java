package food.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    private final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    @Value("${spring.datasource.url}")
    private String database;

    public static void main(String[] args) {
        new BackendApplication().start();
        SpringApplication.run(BackendApplication.class, args);
    }

    public void start() {
        log.info("DATABASE URL: {}", database);
    }

}
