package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Manufacturer;
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
public class ManufacturerDAO {
    final DataSource adminSource;
    final DataSource userSource;

    @Autowired
    public ManufacturerDAO(
            @Qualifier("adminDataSource") DataSource adminSource,
            @Qualifier("userDataSource") DataSource userSource
    ) {
        this.adminSource = adminSource;
        this.userSource = userSource;
    }

    public List<Manufacturer> getAll() throws SQLException {
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
        try (Connection connection = userSource.getConnection()) {
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
            }
        }

        return manufacturers;
    }

    public Manufacturer getByName(String name) throws SQLException {
        Manufacturer manufacturer = null;
        // language=PostgreSQL
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
        try (Connection connection = userSource.getConnection()) {
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
            }
        }

        return manufacturer;
    }
}
