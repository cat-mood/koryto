package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CategoryDAO {
    final DataSource adminSource;
    final DataSource userSource;

    @Autowired
    public CategoryDAO(
            @Qualifier("adminDataSource") DataSource adminSource,
            @Qualifier("userDataSource") DataSource userSource
    ) {
        this.adminSource = adminSource;
        this.userSource = userSource;
    }

    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = """
                SELECT
                    category_id,
                    category_name
                FROM
                    categories;
                """;
        try (Connection connection = userSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Category category = new Category(
                            resultSet.getInt("category_id"),
                            resultSet.getString("category_name")
                    );
                    categories.add(category);
                }
            }
        }

        return categories;
    }

    public Category getByName(String name) throws SQLException {
        Category category = null;
        String query = """
                SELECT
                    category_id,
                    category_name
                FROM
                    categories
                WHERE category_name = ?;
                """;
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    category = new Category(
                            resultSet.getInt("category_id"),
                            resultSet.getString("category_name")
                    );
                }
            }
        }

        return category;
    }
}
