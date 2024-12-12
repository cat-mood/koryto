package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Manufacturer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ManufacturerDAO {
    Connection connection;

    @Autowired
    public ManufacturerDAO(DatabaseConfig databaseConfig) {
        try {
            connection = DriverManager.getConnection(
                    databaseConfig.getURL(),
                    databaseConfig.getUser(),
                    databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            log.error("ManufacturerDAO: ManufactarurerDAO(): " + e.getMessage());
        }
    }

    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = """
                SELECT
                    manufacturer_id,
                    manufacturer_name,
                    manufacturer_address,
                    manufacturer_phone_number
                FROM
                    manufacturers;
                """;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer(
                        resultSet.getInt("manufacturer_id"),
                        resultSet.getString("manufacturer_name"),
                        resultSet.getString("manufacturer_address"),
                        resultSet.getString("manufacturer_phone_number")
                );

                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            log.error("ManufacturerDAO: getAll(): " + e.getMessage());
        }

        return manufacturers;
    }

    public Manufacturer getByName(String name) {
        Manufacturer manufacturer = null;
        String query = """
                SELECT
                    manufacturer_id,
                    manufacturer_name,
                    manufacturer_address,
                    manufacturer_phone_number
                FROM
                    manufacturers
                WHERE manufacturer_name = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                manufacturer = new Manufacturer(
                        resultSet.getInt("manufacturer_id"),
                        resultSet.getString("manufacturer_name"),
                        resultSet.getString("manufacturer_address"),
                        resultSet.getString("manufacturer_phone_number")
                );
            }
        } catch (SQLException e) {
            log.error("ManufacturerDAO: getByName(): " + e.getMessage());
        }

        return manufacturer;
    }
}
