package vn.edu.humg.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourceProperties(
    String driverClassName,
    DataSourceInfo main,
    DataSourceInfo replica
) {
    public record DataSourceInfo(
        String url,
        String username,
        String password
    ) {
    }
}
