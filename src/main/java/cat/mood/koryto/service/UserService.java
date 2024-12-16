package cat.mood.koryto.service;

import cat.mood.koryto.exception.UserExist;
import cat.mood.koryto.model.Car;
import cat.mood.koryto.model.User;
import cat.mood.koryto.model.UserRegister;
import cat.mood.koryto.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserDAO userDAO;
    final PasswordEncoder passwordEncoder;
    final CarService carService;

    User toUser(UserRegister user) throws Exception {
        Car car;
        try {
            car = carService.getCarByBrandAndModel(user.getCarBrandId(), user.getCarModelId());
        } catch (Exception e) {
            car = new Car(0, 0, 0);
        }

        return new User(
                0,
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getCity(),
                user.getAddress(),
                user.getPostIndex(),
                car.getCarId(),
                null,
                user.getEmail()
        );
    }

    public Optional<User> getUserByUsername(String username) throws Exception {
        return userDAO.getUserByUsername(username);
    }

    public List<User> getAll() throws Exception {
        return userDAO.getAll();
    }

    public User getUserById(int id) throws Exception {
        return userDAO.getUserById(id);
    }

    public void updateRole(int id, String role) throws Exception {
        userDAO.updateRoleById(id, role);
    }

    public void registerUser(UserRegister userRegister) throws Exception {
        Optional<User> foundUser = userDAO.getUserByUsername(userRegister.getUsername());
        if (foundUser.isPresent()) {
            throw new UserExist();
        }
        User user = toUser(userRegister);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.insertUser(user);
    }

    public void deleteUser(int id) throws Exception {
        userDAO.deleteUser(id);
    }
}
