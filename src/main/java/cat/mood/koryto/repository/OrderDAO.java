package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.Order;
import cat.mood.koryto.model.OrdersView;
import cat.mood.koryto.model.Part;
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
                    price
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

    public void deleteOrder(OrdersView ordersView) {
        // TODO
    }

    public void createOrder(Order order) {
        String insertOrder = """
                INSERT INTO orders (user_id, cost, created_at) VALUES
                (?, ?, ?);
                """;

        String insertParts = """
                INSERT INTO orders_body (order_id, part_id, amount) VALUES
                (?, ?, ?);
                """;

        int rows = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrder)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setDouble(2, order.getCost());
            preparedStatement.setTimestamp(3, order.getCreatedAt());

            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("OrderDAO.createOrder: {}", e.getMessage());
            return;
        }

        log.info("OrderDAO.createOrder: {} rows affected", rows);

//        try (PreparedStatement preparedStatement = connection.prepareStatement(insertParts)) {
//            for (Cart part : parts) {
//                preparedStatement.setInt(1, order.getOrderId());
//                preparedStatement.setInt(2, part.getPartId());
//                preparedStatement.setShort(3, part.getAmount());
//            }
//
//            rows = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            log.error("OrderDAO.createOrder: {}", e.getMessage());
//        }
//
//        log.info("OrderDAO.createOrder: {} rows affected", rows);

//        if (rows == 0) {
//            deleteOrder(ordersViews.get(0));
//        }
    }
}
