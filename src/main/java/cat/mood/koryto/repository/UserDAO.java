package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Optional;

@Repository
@Slf4j
public class UserDAO {
    private Connection connection;

    @Autowired
    public UserDAO(DatabaseConfig config) {
        try {
            String url = "jdbc:postgresql://"
                    + config.getHost()
                    + ":"
                    + config.getPort()
                    + "/"
                    + config.getName();
            connection = DriverManager.getConnection(url, config.getUser(), config.getPassword());
        } catch (SQLException e) {
            log.error("UserDAO constructor: {}", e.getMessage());
        }
    }

    public Optional<User> getUserByUsername(String username) {
        String query = """
                SELECT
                    user_id,
                    username,
                    password,
                    first_name,
                    middle_name,
                    last_name,
                    birth_date,
                    city,
                    address,
                    post_index,
                    car_id
                FROM
                    users
                WHERE username='%s';
                """;
        ResultSet rs = null;
        User user;
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery(String.format(query, username));
            rs.next();
            user = new User(rs);
        } catch (SQLException e) {
            log.error("UserDAO getUserByUsername: " + e.getMessage());
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
