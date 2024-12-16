package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchParameters {
    String search;
    Integer categoryId;
    Integer carModelId;
    Integer manufacturerId;
    Double minPrice;
    Double maxPrice;
}
