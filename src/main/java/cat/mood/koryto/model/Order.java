package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Order {
    Integer orderId;
    Integer userId;
    Double cost;
    Timestamp createdAt;
}
