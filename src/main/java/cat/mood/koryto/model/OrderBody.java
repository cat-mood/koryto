package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderBody {
    Integer orderId;
    Integer partId;
    Short amount;
}
