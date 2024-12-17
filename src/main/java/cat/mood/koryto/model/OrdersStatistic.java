package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersStatistic {
    Integer partId;
    String partName;
    Long soldAmount;
    Double income;
}
