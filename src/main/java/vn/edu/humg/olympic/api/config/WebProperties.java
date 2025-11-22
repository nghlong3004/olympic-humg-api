package vn.edu.humg.olympic.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cors")
public record WebProperties(
    /*
     * This is CORS configuration.
     * When a web application uses this server directly, we should check CORS configuration like this.
     * However, we recommend to use CORS configuration on API Gateway strongly.
     */
    List<String> client
) {
}
