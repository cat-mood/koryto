package cat.mood.koryto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class PartView implements Comparable<PartView> {
    String partName;
    String categoryName;
    String manufacturerName;
    String manufacturerAddress;
    String manufacturerPhoneNumber;
    String carBrandName;
    String carModelName;
    String partDescription;
    Integer partId;
    Integer categoryId;
    Integer manufacturerId;
    Integer carBrandId;
    Integer carModelId;
    Double price;
    Integer carId;

    @Override
    public int compareTo(PartView o) {
        return this.partId.compareTo(o.partId);
    }
}
