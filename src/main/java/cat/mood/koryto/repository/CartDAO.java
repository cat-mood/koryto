package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.CartView;
import cat.mood.koryto.model.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CartDAO {
    Connection connection;

    @Autowired
    public CartDAO(DatabaseConfig databaseConfig) {
        try {
            connection = DriverManager.getConnection(
                    databaseConfig.getURL(),
                    databaseConfig.getUser(),
                    databaseConfig.getPassword()
            );
        } catch (SQLException e) {
            log.error("CartDAO: CartDAO(): " + e.getMessage());
        }
    }

    CartView buildCartView(ResultSet resultSet) throws SQLException {
        return new CartView(
                resultSet.getInt("part_id"),
                resultSet.getString("part_name"),
                resultSet.getDouble("price"),
                resultSet.getInt("user_id"),
                resultSet.getShort("amount")
        );
    }

    public List<CartView> getCartByUserId(int userId) {
        String query = """
                SELECT
                    part_id,
                    part_name,
                    price,
                    user_id,
                    amount
                FROM
                    cart_view
                WHERE user_id = ?;
                """;
        List<CartView> cartViews = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartView cartView = buildCartView(resultSet);
                cartViews.add(cartView);
            }
        } catch (SQLException e) {
            log.error("CartDAO: getCartByUserId(): " + e.getMessage());
        }

        return cartViews;
    }

    public void addPartToCart(Cart cart) {
        String query = """
                INSERT INTO cart (user_id, part_id, amount) VALUES
                (?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cart.getUserId());
            statement.setInt(2, cart.getPartId());
            statement.setShort(3, cart.getAmount());
            int rows = statement.executeUpdate();

            log.info("CartDAO: addPartToCart(): {} rows affected", rows);
        } catch (SQLException e) {
            log.error("CartDAO: addPartToCart(): {}", e.getMessage());
        }
    }

    public void updatePart(Cart cart) {
        String query = """
                UPDATE cart SET amount = ? WHERE user_id = ? AND part_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setShort(1, cart.getAmount());
            statement.setInt(2, cart.getUserId());
            statement.setInt(3, cart.getPartId());

            int rows = statement.executeUpdate();
            log.info("CartDAO: updatePart(): {} rows affected", rows);
        } catch (SQLException e) {
            log.error("CartDAO: updatePart(): {}", e.getMessage());
        }
    }

    public void deletePart(Cart cart) {
        String query = """
                DELETE FROM cart WHERE user_id = ? AND part_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cart.getUserId());
            statement.setInt(2, cart.getPartId());

            int rows = statement.executeUpdate();
            log.info("CartDAO: deletePart(): {} rows affected", rows);
        } catch (SQLException e) {
            log.error("CartDAO: deletePart(): {}", e.getMessage());
        }
    }
}
