package cat.mood.koryto.service;

import cat.mood.koryto.exception.UserExist;
import cat.mood.koryto.model.User;
import cat.mood.koryto.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserDAO userDAO;
    final PasswordEncoder passwordEncoder;

    public Optional<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public void registerUser(User user) throws UserExist {
        Optional<User> foundUser = userDAO.getUserByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            throw new UserExist();
        }
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.insertUser(user);
    }
}
