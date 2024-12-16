package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.User;
import cat.mood.koryto.model.UserRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserDAO {
    final DataSource adminSource;
    final DataSource userSource;

    @Autowired
    public UserDAO(
            @Qualifier("adminDataSource") DataSource adminSource,
            @Qualifier("userDataSource") DataSource userSource
    ) {
        this.adminSource = adminSource;
        this.userSource = userSource;
    }

    User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("user_id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("first_name"),
                resultSet.getString("middle_name"),
                resultSet.getString("last_name"),
                resultSet.getDate("birth_date"),
                resultSet.getString("city"),
                resultSet.getString("address"),
                resultSet.getShort("post_index"),
                resultSet.getInt("car_id"),
                resultSet.getString("role"),
                resultSet.getString("email")
        );
    }

    public List<User> getAll() throws SQLException {
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
                    users;
                """;
        List<User> users = new ArrayList<>();
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    User user = buildUser(resultSet);

                    users.add(user);
                }
            }
        }

        return users;
    }

    public Optional<User> getUserByUsername(String username) throws SQLException {
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
        User user = null;
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    user = buildUser(rs);
                }
            }
        }

        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public User getUserById(int id) throws SQLException {
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
                WHERE user_id = ?;
                """;
        User user = null;
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    user = buildUser(resultSet);
                }
            }
        }

        return user;
    }

    public void insertUser(User user) throws SQLException {
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
        try (Connection connection = userSource.getConnection()) {
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
                    stmt.setInt(10, user.getCarId());
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
                log.info("UserDAO.insertUser: {} rows inserted", rows);
            }
        }
    }

    public void updateRoleById(int id, String role) throws SQLException {
        String query = """
                UPDATE users SET role = ?
                WHERE user_id = ?;
                """;

        try (Connection connection = adminSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, role);
                statement.setInt(2, id);

                statement.executeUpdate();
            }
        }
    }

    public void deleteUser(int id) throws SQLException {
        String query = """
                DELETE FROM users WHERE user_id = ?;
                """;
        try (Connection connection = adminSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
    }
}
