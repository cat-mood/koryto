package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class OrdersView {
    Integer orderId;
    Integer userId;
    Double cost;
    Timestamp createdAt;
    Integer partId;
    String partName;
    Integer categoryId;
    Integer manufacturerId;
    Integer carId;
    String partDescription;
    Double price;
    Short amount;
}
