package cat.mood.koryto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class User implements UserDetails {
    long userId;
    String username;
    String password;
    String firstName;
    String middleName;
    String lastName;
    Date birthDate;
    String city;
    String address;
    short postIndex;
    long carId;

    public User(ResultSet rs) throws SQLException {
        userId = rs.getLong("user_id");
        username = rs.getString("username");
        password = rs.getString("password");
        firstName = rs.getString("first_name");
        middleName = rs.getString("middle_name");
        lastName = rs.getString("last_name");
        birthDate = rs.getDate("birth_date");
        city = rs.getString("city");
        address = rs.getString("address");
        postIndex = rs.getShort("post_index");
        carId = rs.getLong("car_id");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
