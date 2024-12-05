package cat.mood.koryto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("cat.mood.koryto")
@Data
public class DatabaseConfig {
    private String host;
    private String name;
    private String user;
    private String password;
    private String port;

    public String getURL() {
        return "jdbc:postgresql://"
                + host
                + ":"
                + port
                + "/"
                + name;
    }
}
