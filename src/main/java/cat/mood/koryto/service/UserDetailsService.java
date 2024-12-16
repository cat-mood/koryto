package cat.mood.koryto.service;

import cat.mood.koryto.model.User;
import cat.mood.koryto.repository.UserDAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user;
        try {
            user = userDAO.getUserByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username);
        }
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }
}
