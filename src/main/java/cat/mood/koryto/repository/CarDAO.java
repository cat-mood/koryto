package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CarDAO {
    Connection connection;

    @Autowired
    public CarDAO(DatabaseConfig databaseConfig) {
        String url = databaseConfig.getURL();
        try {
            connection = DriverManager.getConnection(
                    url, databaseConfig.getUser(), databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            log.error("CarDAO: " + e.getMessage());
        }
    }

    public List<String> getAllBrands() {
        List<String> brands = new ArrayList<>();

        String query = """
                SELECT
                car_brand_name
                FROM
                car_brands;
                """;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                brands.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            log.error("CarDAO getAllBrands: " + e.getMessage());
        }

        return brands;
    }

    public List<String> getModelsByBrand(String brand) {
        List<String> models = new ArrayList<>();

        String query = """
                SELECT
                car_model_name
                FROM
                cars_view
                WHERE car_brand_name = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, brand);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                models.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            log.error("CarDAO getModelsByBrand: " + e.getMessage());
        }

        return models;
    }

    public Car getCarByBrandAndModel(String brand, String model) {
        Car car = null;
        String query = """
                SELECT
                    car_brand_name,
                    car_model_name,
                    car_brand_id,
                    car_model_id,
                    car_id
                FROM
                    cars_view
                WHERE car_brand_name = ? AND car_model_name = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, brand);
            statement.setString(2, model);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car = new Car(
                        resultSet.getInt("car_id"),
                        resultSet.getString("car_brand_name"),
                        resultSet.getString("car_model_name")
                );
            } else {
                log.info("Car getCarByBrandAndModel: doesn't exist");
            }
        } catch (SQLException e) {
            log.error("CarDAO getCarByBrandAndModel(): " + e.getMessage());
        }

        return car;
    }
}
