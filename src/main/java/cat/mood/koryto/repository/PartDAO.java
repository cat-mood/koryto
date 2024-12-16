package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
@Slf4j
public class PartDAO {
    final DataSource adminSource;
    final DataSource userSource;

    @Autowired
    public PartDAO(
            @Qualifier("adminDataSource") DataSource adminSource,
            @Qualifier("userDataSource") DataSource userSource
    ) {
        this.adminSource = adminSource;
        this.userSource = userSource;
    }

    PartView buildPartView(ResultSet resultSet) throws SQLException {
        return new PartView(
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

    public List<PartView> getAll() throws SQLException {
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
        try (Connection connection = userSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    PartView part = buildPartView(resultSet);
                    parts.add(part);
                }
            }
        }

        return parts;
    }

    public PartView getById(int id) throws SQLException {
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
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    part = buildPartView(resultSet);
                }
            }
        }

        return part;
    }

    public void add(Part part) throws SQLException {
        String query = """
                INSERT INTO parts (part_name, category_id, manufacturer_id, car_id, part_description, price) VALUES
                (?, ?, ?, ?, ?, ?);
                """;
        try (Connection connection = adminSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, part.getPartName());
                statement.setInt(2, part.getCategoryId());
                statement.setInt(3, part.getManufacturerId());
                statement.setInt(4, part.getCarId());
                statement.setString(5, part.getPartDescription());
                statement.setDouble(6, part.getPrice());

                int affectedRows = statement.executeUpdate();
                log.info("PartDAO.add(): {} rows affected", affectedRows);
            }
        }
    }

    public void update(Part part) throws SQLException {
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
        int rows = 0;
        try (Connection connection = adminSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, part.getPartName());
                statement.setInt(2, part.getCategoryId());
                statement.setInt(3, part.getManufacturerId());
                statement.setInt(4, part.getCarId());
                statement.setString(5, part.getPartDescription());
                statement.setDouble(6, part.getPrice());
                statement.setInt(7, part.getPartId());

                rows = statement.executeUpdate();
            }
        }

        log.info("PartDAO.update(): {} rows affected", rows);
    }

    public List<PartView> getByBrandAndModel(int brandId, int modelId) throws SQLException {
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
                    parts_view
                WHERE car_brand_id = ? AND car_model_id = ?;
                """;

        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, brandId);
                statement.setInt(2, modelId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    PartView part = buildPartView(resultSet);
                    parts.add(part);
                }
            }
        }

        return parts;
    }
}
