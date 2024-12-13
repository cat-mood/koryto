package cat.mood.koryto.service;

import cat.mood.koryto.exception.UserExist;
import cat.mood.koryto.model.User;
import cat.mood.koryto.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserDAO userDAO;
    final PasswordEncoder passwordEncoder;

    public Optional<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public void updateRole(int id, String role) {
        userDAO.updateRoleById(id, role);
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

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
