package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDAO {
    Connection connection;

    @Autowired
    public OrderDAO(DatabaseConfig databaseConfig) {
        try {
            connection = DriverManager.getConnection(
                    databaseConfig.getURL(),
                    databaseConfig.getUser(),
                    databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            log.error("OrderDAO.OrderDAO: {}", e.getMessage());
        }
    }

    OrdersView buildOrdersView(ResultSet resultSet) throws SQLException {
        return new OrdersView(
                resultSet.getInt("order_id"),
                resultSet.getInt("user_id"),
                resultSet.getDouble("cost"),
                resultSet.getTimestamp("created_at"),
                resultSet.getInt("part_id"),
                resultSet.getString("part_name"),
                resultSet.getInt("category_id"),
                resultSet.getInt("manufacturer_id"),
                resultSet.getInt("car_id"),
                resultSet.getString("part_description"),
                resultSet.getDouble("price"),
                resultSet.getShort("amount")
        );
    }

    public List<OrdersView> getOrdersByUserId(int userId) {
        List<OrdersView> orders = new ArrayList<>();
        String query = """
                SELECT
                    order_id,
                    user_id,
                    cost,
                    created_at,
                    part_id,
                    part_name,
                    category_id,
                    manufacturer_id,
                    car_id,
                    part_description,
                    price,
                    amount
                FROM
                    orders_view
                WHERE user_id = ?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orders.add(buildOrdersView(resultSet));
            }
        } catch (SQLException e) {
            log.error("OrderDAO.getOrdersByUserId: {}", e.getMessage());
        }

        return orders;
    }

    public void deleteOrder(int orderId) {
        //language=PostgreSQL
        String query = """
                DELETE FROM orders WHERE order_id = ?;
                DELETE FROM orders_body WHERE order_id = ?;
                """;

        int rows = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.setInt(2, orderId);

            rows = statement.executeUpdate();
        } catch (SQLException e) {
            log.error("OrderDAO.deleteOrder: {}", e.getMessage());
        }

        log.info("OrderDAO.deleteOrder: {} rows affected", rows);
    }

    public void createOrderBody(List<OrderBody> orderBodyList) throws SQLException {
        //language=PostgreSQL
        String insertParts = """
                INSERT INTO orders_body (order_id, part_id, amount) VALUES
                (?, ?, ?);
                """;

        int[] result = null;

        try (PreparedStatement statement = connection.prepareStatement(insertParts)) {
            for (OrderBody orderBody : orderBodyList) {
                statement.setInt(1, orderBody.getOrderId());
                statement.setInt(2, orderBody.getPartId());
                statement.setDouble(3, orderBody.getAmount());

                statement.addBatch();
            }

            result = statement.executeBatch();
        }

        log.info("OrderDAO.createOrderBody: {} rows affected", result.length);
    }

    // Return id
    public int createOrder(Order order) throws SQLException {
        //language=PostgreSQL
        String insertOrder = """
                INSERT INTO orders (user_id, cost, created_at) VALUES
                (?, ?, ?)
                RETURNING order_id;
                """;

        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrder)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setDouble(2, order.getCost());
            preparedStatement.setTimestamp(3, order.getCreatedAt());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("order_id");
            }
        }

        return id;
    }
}
