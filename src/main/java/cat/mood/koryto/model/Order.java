package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Comparator;

@Data
@AllArgsConstructor
public class Order {
    Integer orderId;
    Integer userId;
    Double cost;
    Timestamp createdAt;

    // from new to old
    public static Comparator<Order> timestampComparator =
            (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt());
}
