package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Manufacturer {
    Integer manufacturerId;
    String manufacturerName;
    String manufacturerAddress;
    String manufacturerPhoneNumber;
}
