package cat.mood.koryto.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Part {
    @NotBlank
    String partName;
    String categoryName;
    String manufacturerName;
    String manufacturerAddress;
    String manufacturerPhoneNumber;
    String carBrandName;
    String carModelName;
    String partDescription;
}
