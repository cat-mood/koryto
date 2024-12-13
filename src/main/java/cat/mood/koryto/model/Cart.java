package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cart {
    Integer userId;
    Integer partId;
    Short amount;
}
