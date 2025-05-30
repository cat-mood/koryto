package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Comparator;

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

    @Override
    public String toString() {
        return "OrdersView{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                ", partId=" + partId +
                ", partName='" + partName + '\'' +
                ", categoryId=" + categoryId +
                ", manufacturerId=" + manufacturerId +
                ", carId=" + carId +
                ", partDescription='" + partDescription + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
