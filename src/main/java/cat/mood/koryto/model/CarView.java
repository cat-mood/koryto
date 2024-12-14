package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarView {
    Integer carId;
    Integer carBrandId;
    Integer carModelId;
    String carBrandName;
    String carModelName;
}
