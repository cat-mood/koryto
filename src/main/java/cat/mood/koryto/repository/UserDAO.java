package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
            log.error("UserDAO: {}", e.getMessage());
        }
    }

//    Optional<User> getUserByUsername(String username) {
//        String query = """
//                SELECT
//
//                FROM
//
//                """;
//        try (Statement stmt = connection.createStatement()) {
//
//        }
//    }
}
