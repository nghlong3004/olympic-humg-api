package vn.edu.humg.olympic.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "client")
public record ClientProperties(
    Config config,
    Service profile,
    Service auth
) {
    public record Config(Defaults defaults) {
        public record Defaults(
            Duration connectTimeout,
            Duration readTimeout
        ) {
        }
    }

    public record Service(String url) {
    }
}
