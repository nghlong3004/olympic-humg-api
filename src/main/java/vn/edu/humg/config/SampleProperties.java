package vn.edu.humg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sample.client")
public record SampleProperties(
    String clientId,
    String secret
) {
}
