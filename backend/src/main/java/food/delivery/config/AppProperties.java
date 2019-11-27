package food.delivery.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author szendihorvath
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String imagePath;

    @Data
    public static class Mail {
        private String baseUrl;
        private String from;
    }
}
