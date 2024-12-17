package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersStatistic {
    Integer userId;
    String username;
    Integer averageOrderSize;
    Double totalIncome;
}
