package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Car {
    Integer carId;
    Integer carBrandId;
    Integer carModelId;
}
