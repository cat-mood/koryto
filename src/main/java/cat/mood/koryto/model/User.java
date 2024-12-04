package cat.mood.koryto.model;

import jakarta.validation.constraints.NotBlank;
import liquibase.change.DatabaseChangeNote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User implements UserDetails {
    Long userId;
    @NotBlank
    String username;
    @NotBlank
    String password;
    String firstName;
    String middleName;
    String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthDate;
    String city;
    String address;
    Short postIndex;
    Long carId;
    String role;
    String email;

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
        role = rs.getString("role");
        email = rs.getString("email");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        return List.of(grantedAuthority);
    }
}
