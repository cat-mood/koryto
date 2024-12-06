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
            String url = config.getURL();
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
                    car_id,
                    role,
                    email
                FROM
                    users
                WHERE username=?;
                """;
        User user;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            user = new User(
                    rs.getLong("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("last_name"),
                    rs.getDate("birth_date"),
                    rs.getString("city"),
                    rs.getString("address"),
                    rs.getShort("post_index"),
                    rs.getLong("car_id"),
                    rs.getString("role"),
                    rs.getString("email")
            );
        } catch (SQLException e) {
            log.error("UserDAO getUserByUsername: " + e.getMessage());
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public void insertUser(User user) {
        String query = """
                INSERT INTO users (
                    username,
                    password,
                    first_name,
                    middle_name,
                    last_name,
                    birth_date,
                    city,
                    address,
                    post_index,
                    car_id,
                    role,
                    email
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
                stmt.setString(3, user.getFirstName());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }
            if (user.getMiddleName() != null && !user.getMiddleName().isEmpty()) {
                stmt.setString(4, user.getMiddleName());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            if (user.getLastName() != null && !user.getLastName().isEmpty()) {
                stmt.setString(5, user.getLastName());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }
            if (user.getBirthDate() != null) {
                stmt.setDate(6, user.getBirthDate());
            } else {
                stmt.setNull(6, Types.DATE);
            }
            if (user.getCity() != null && !user.getCity().isEmpty()) {
                stmt.setString(7, user.getCity());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }
            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                stmt.setString(8, user.getAddress());
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }
            if (user.getPostIndex() != null) {
                stmt.setShort(9, user.getPostIndex());
            } else {
                stmt.setNull(9, Types.SMALLINT);
            }
            if (user.getCarId() != null) {
                stmt.setLong(10, user.getCarId());
            } else {
                stmt.setNull(10, Types.BIGINT);
            }
            stmt.setString(11, user.getRole());
            if (user.getEmail() != null) {
                stmt.setString(12, user.getEmail());
            } else {
                stmt.setNull(12, Types.VARCHAR);
            }
            int rows = stmt.executeUpdate();
            log.info(rows + " rows inserted");
        } catch (SQLException e) {
            log.error("UserDAO insertUser: " + e.getMessage());
        }
    }
}
