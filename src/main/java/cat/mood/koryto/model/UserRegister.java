package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@AllArgsConstructor
public class UserRegister {
    String username;
    String password;
    String firstName;
    String middleName;
    String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthDate;
    String city;
    String address;
    Short postIndex;
    Integer carId;
    String email;
    String carBrandName;
    String carModelName;
}
