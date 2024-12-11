package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
@Slf4j
public class PartDAO {
    Connection connection;

    @Autowired
    public PartDAO(DatabaseConfig databaseConfig) {
        String url = databaseConfig.getURL();

        try {
            connection = DriverManager.getConnection(
                    url,
                    databaseConfig.getUser(),
                    databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            log.error("PartDAO: " + e.getMessage());
        }
    }

    public List<Part> getAll() {
        List<Part> parts = new ArrayList<>();
        String query = """
                SELECT
                    part_name,
                    category_name,
                    manufacturer_name,
                    manufacturer_address,
                    manufacturer_phone_number,
                    car_brand_name,
                    car_model_name,
                    part_description,
                    part_id,
                    category_id,
                    manufacturer_id,
                    car_brand_id,
                    car_model_id,
                    price
                FROM
                    parts_view;
                """;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Part part = new Part(
                        resultSet.getString("part_name"),
                        resultSet.getString("category_name"),
                        resultSet.getString("manufacturer_name"),
                        resultSet.getString("manufacturer_address"),
                        resultSet.getString("manufacturer_phone_number"),
                        resultSet.getString("car_brand_name"),
                        resultSet.getString("car_model_name"),
                        resultSet.getString("part_description"),
                        resultSet.getInt("part_id"),
                        resultSet.getInt("category_id"),
                        resultSet.getInt("manufacturer_id"),
                        resultSet.getInt("car_brand_id"),
                        resultSet.getInt("car_model_id"),
                        resultSet.getInt("price")
                );
                parts.add(part);
            }
        } catch (SQLException e) {
            log.error("PartDAO getAll: " + e.getMessage());
        }

        return parts;
    }
}
