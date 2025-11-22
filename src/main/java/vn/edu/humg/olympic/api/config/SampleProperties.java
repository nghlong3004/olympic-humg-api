package vn.edu.humg.olympic.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "sample.client")
public record SampleProperties(
    String clientId,
    String secret
) {
}
