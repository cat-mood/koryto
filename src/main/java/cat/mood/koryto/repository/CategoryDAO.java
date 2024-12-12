package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CategoryDAO {
    Connection connection;

    @Autowired
    public CategoryDAO(DatabaseConfig databaseConfig) {
        String url = databaseConfig.getURL();
        try {
            connection = DriverManager.getConnection(
                url,
                    databaseConfig.getUser(),
                    databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            log.error("CategoryDAO: CategoryDAO(): " + e.getMessage());
        }
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String query = """
                SELECT
                    category_id,
                    category_name
                FROM
                    categories;
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            log.error("CategoryDAO: getAllCategory(): " + e.getMessage());
        }

        return categories;
    }

    public Category getByName(String name) {
        Category category = null;
        String query = """
                SELECT
                    category_id,
                    category_name
                FROM
                    categories
                WHERE category_name = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category = new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name")
                );
            }
        } catch (SQLException e) {
            log.error("CategoryDAO: getByName(): " + e.getMessage());
        }

        return category;
    }
}
