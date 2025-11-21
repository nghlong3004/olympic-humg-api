package vn.edu.humg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ConfigurationPropertiesScan("vn.edu.humg.config")
public class HumgApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HumgApiApplication.class, args);
    }
}
