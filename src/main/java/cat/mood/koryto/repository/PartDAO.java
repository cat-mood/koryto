package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
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

    public List<PartView> getAll() {
        List<PartView> parts = new ArrayList<>();
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
                PartView part = new PartView(
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
                        resultSet.getDouble("price")
                );
                parts.add(part);
            }
        } catch (SQLException e) {
            log.error("PartDAO getAll: " + e.getMessage());
        }

        return parts;
    }

    public PartView getById(int id) {
        PartView part = null;
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
                    parts_view
                WHERE part_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                part = new PartView(
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
                        resultSet.getDouble("price")
                );
            }
        } catch (SQLException e) {
            log.error("PartDAO getById(): " + e.getMessage());
        }

        return part;
    }

    public void add(Part part) {
        String query = """
                INSERT INTO parts (part_name, category_id, manufacturer_id, car_id, part_description, price) VALUES
                (?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, part.getPartName());
            statement.setInt(2, part.getCategoryId());
            statement.setInt(3, part.getManufacturerId());
            statement.setInt(4, part.getCarId());
            statement.setString(5, part.getPartDescription());
            statement.setDouble(6, part.getPrice());

            int affectedRows = statement.executeUpdate();
            log.info("PartDAO add(): affected rows: " + affectedRows);
        } catch (SQLException e) {
            log.error("PartDAO add(): " + e.getMessage());
        }
    }

    public void update(Part part) {
        String query = """
                UPDATE parts
                SET part_name = ?,
                    category_id = ?,
                    manufacturer_id = ?,
                    car_id = ?,
                    part_description = ?,
                    price = ?
                WHERE part_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, part.getPartName());
            statement.setInt(2, part.getCategoryId());
            statement.setInt(3, part.getManufacturerId());
            statement.setInt(4, part.getCarId());
            statement.setString(5, part.getPartDescription());
            statement.setDouble(6, part.getPrice());
            statement.setInt(7, part.getPartId());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("PartDAO update(): " + e.getMessage());
        }
    }
}
