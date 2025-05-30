package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {
    int partId;
    String partName;
    short amount;
    double price;

    @Override
    public String toString() {
        return "CartItem{" +
                "partId=" + partId +
                ", partName='" + partName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
