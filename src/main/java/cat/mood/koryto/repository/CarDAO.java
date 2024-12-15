package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Car;
import cat.mood.koryto.model.CarView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CarDAO {
    // TODO: make connection pool
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

    CarView buildCarView(ResultSet resultSet) throws SQLException {
        return new CarView(
                resultSet.getInt("car_id"),
                resultSet.getInt("car_brand_id"),
                resultSet.getInt("car_model_id"),
                resultSet.getString("car_brand_name"),
                resultSet.getString("car_model_name")
        );
    }

    Car buildCar(ResultSet resultSet) throws SQLException {
        return new Car(
                resultSet.getInt("car_id"),
                resultSet.getInt("car_brand_id"),
                resultSet.getInt("car_model_id")
        );
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

    public Car getCarByBrandAndModel(int brandId, int modelId) {
        Car car = null;
        String query = """
                SELECT
                    car_brand_id,
                    car_model_id,
                    car_id
                FROM
                    cars
                WHERE car_brand_id = ? AND car_model_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, brandId);
            statement.setInt(2, modelId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car = buildCar(resultSet);
            } else {
                log.info("Car getCarByBrandAndModel: doesn't exist");
            }
        } catch (SQLException e) {
            log.error("CarDAO getCarByBrandAndModel(): {}", e.getMessage());
        }

        return car;
    }

    public Car getCarByBrandNameAndModelName(String brand, String model) {
        Car car = null;
        String query = """
                SELECT
                    car_id,
                    car_brand_id,
                    car_model_id
                FROM
                    cars_view
                WHERE car_brand_name = ? AND car_model_name = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, brand);
            statement.setString(2, model);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car = buildCar(resultSet);
            }
        } catch (SQLException e) {
            log.error("CarDAO getCarByBrandNameAndModelName(): {}", e.getMessage());
        }

        return car;
    }

    public Car getCarById(int carId) {
        Car car = null;
        String query = """
                SELECT
                    car_id,
                    car_brand_id,
                    car_model_id
                FROM
                    cars
                WHERE car_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car = buildCar(resultSet);
            }
        } catch (SQLException e) {
            log.error("CarDAO getCarById: {}",  e.getMessage());
        }

        return car;
    }
}
