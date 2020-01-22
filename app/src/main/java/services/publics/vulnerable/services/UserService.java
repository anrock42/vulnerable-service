package services.publics.vulnerable.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import services.publics.vulnerable.db.UserRepository;
import services.publics.vulnerable.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        return jdbcTemplate.query(
                String.format("SELECT * FROM user WHERE email LIKE '%%%s%%'", email),
                new RowMapperResultSetExtractor<>((rs, rowNum) -> new User(rs.getString("id"), rs.getString("email"), rs.getString("password")))
        );
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
