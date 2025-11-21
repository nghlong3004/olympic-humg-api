package vn.edu.humg.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

/**
 * This is for annotation based read, write db selection
 * If you add following annotation, it will use read replica only.
 * "@Transactional(readOnly = true)"
 */
@Configuration
public class DataSourceConfiguration {

    private final DataSourceProperties properties;

    /**
     * For DB connection performance, keep at least 1 connection.
     */
    private static final int MINIMUM_IDLE = 1;

    private static final int MAXIMUM_POOL_SIZE = 10;

    public DataSourceConfiguration(DataSourceProperties properties) {
        this.properties = properties;
    }

    public DataSource writeDataSource() {
        return createDataSource(
            properties.main().url(),
            properties.main().username(),
            properties.main().password()
        );
    }

    public DataSource readDataSource() {
        return createDataSource(
            properties.replica().url(),
            properties.replica().username(),
            properties.replica().password()
        );
    }

    @Bean
    public DataSource createRouterDatasource() {
        var routingDataSource = new RoutingDataSource();
        var targetDataSources = Map.<Object, Object>of(
            "write", writeDataSource(),
            "read", readDataSource()
        );
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());

        routingDataSource.afterPropertiesSet();
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    private DataSource createDataSource(String url, String user, String password) {
        var dataSource = new HikariDataSource();
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setAutoCommit(false);
        dataSource.setDriverClassName(properties.driverClassName());
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        dataSource.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        dataSource.setMinimumIdle(MINIMUM_IDLE);
        return dataSource;
    }
}
