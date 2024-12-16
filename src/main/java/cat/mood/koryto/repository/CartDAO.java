package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.CartView;
import cat.mood.koryto.model.Part;
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
public class CartDAO {
    final DataSource adminSource;
    final DataSource userSource;

    @Autowired
    public CartDAO(
            @Qualifier("adminDataSource") DataSource adminSource,
            @Qualifier("userDataSource") DataSource userSource
    ) {
        this.adminSource = adminSource;
        this.userSource = userSource;
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

    public List<CartView> getCartByUserId(int userId) throws SQLException {
        // language=PostgreSQL
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
        try (Connection connection = userSource.getConnection()) {
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
        }

        return cartViews;
    }

    public void addPartToCart(Cart cart) throws SQLException {
        // language=PostgreSQL
        String query = """
                INSERT INTO cart (user_id, part_id, amount) VALUES
                (?, ?, ?);
                """;
        int rows = 0;
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, cart.getUserId());
                statement.setInt(2, cart.getPartId());
                statement.setShort(3, cart.getAmount());
                rows = statement.executeUpdate();
            }
        }

        log.info("CartDAO.addPartToCart(): {} rows affected", rows);
    }

    public void updatePart(Cart cart) throws SQLException {
        // language=PostgreSQL
        String query = """
                UPDATE cart SET amount = ? WHERE user_id = ? AND part_id = ?;
                """;
        int rows = 0;
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setShort(1, cart.getAmount());
                statement.setInt(2, cart.getUserId());
                statement.setInt(3, cart.getPartId());

                rows = statement.executeUpdate();
            }
        }

        log.info("CartDAO.updatePart(): {} rows affected", rows);
    }

    public void deletePart(Cart cart) throws SQLException {
        String query = """
                DELETE FROM cart WHERE user_id = ? AND part_id = ?;
                """;
        int rows = 0;
        try (Connection connection = userSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, cart.getUserId());
                statement.setInt(2, cart.getPartId());

                rows = statement.executeUpdate();
            }
        }

        log.info("CartDAO.deletePart(): {} rows affected", rows);
    }
}
