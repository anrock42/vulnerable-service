package services.publics.vulnerable.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jboss.logging.Field;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import services.publics.vulnerable.db.UserRepository;
import services.publics.vulnerable.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserService {

    JdbcTemplate jdbcTemplate;
    UserRepository userRepository;

    @NotNull
    public List<User> findUsers(@RequestParam("email") String email) {
        // SQL Injection
        return jdbcTemplate.queryForList(String.format("SELECT * FROM users WHERE email LIKE '%s'", email), User.class);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
