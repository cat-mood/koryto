package cat.mood.koryto.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties("cat.mood.koryto.datasource")
@Data
public class DataSourceConfig {
    int maxPoolSize;
    String host;
    String name;
    String admin;
    String adminPassword;
    String port;
    String user;
    String userPassword;

    @Bean(name = "adminDataSource")
    public DataSource adminDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + name);
        config.setUsername(admin);
        config.setPassword(adminPassword);
        config.setMaximumPoolSize(maxPoolSize);

        return new HikariDataSource(config);
    }

    @Bean(name = "userDataSource")
    public DataSource userDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + name);
        config.setUsername(user);
        config.setPassword(userPassword);
        config.setMaximumPoolSize(maxPoolSize);

        return new HikariDataSource(config);
    }
}
