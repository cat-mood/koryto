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
    @JsonProperty("partName")
    String partName;
    @JsonProperty("categoryName")
    String categoryName;
    @JsonProperty("manufacturerName")
    String manufacturerName;
    @JsonProperty("manufacturerAddress")
    String manufacturerAddress;
    @JsonProperty("manufacturerPhoneNumber")
    String manufacturerPhoneNumber;
    @JsonProperty("carBrandName")
    String carBrandName;
    @JsonProperty("carModelName")
    String carModelName;
    @JsonProperty("partDescription")
    String partDescription;
    @JsonProperty("partId")
    Integer partId;
    @JsonProperty("categoryId")
    Integer categoryId;
    @JsonProperty("manufacturerId")
    Integer manufacturerId;
    @JsonProperty("carBrandId")
    Integer carBrandId;
    @JsonProperty("carModelId")
    Integer carModelId;
    @JsonProperty("price")
    Double price;

    @Override
    public int compareTo(PartView o) {
        return this.partId.compareTo(o.partId);
    }
}
