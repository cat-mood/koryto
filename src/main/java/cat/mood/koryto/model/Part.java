package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Part {
    Integer partId;
    String partName;
    Integer categoryId;
    Integer manufacturerId;
    Integer carId;
    String partDescription;
    Double price;
}
