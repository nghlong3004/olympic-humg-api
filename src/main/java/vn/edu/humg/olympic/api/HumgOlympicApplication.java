package vn.edu.humg.olympic.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HumgOlympicApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumgOlympicApplication.class, args);
    }

}
