package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartView {
    Integer partId;
    String partName;
    Double price;
    Integer user_id;
    Short amount;
}
