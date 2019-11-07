package services.publics.vulnerable.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import services.publics.vulnerable.db.UserRepository;
import services.publics.vulnerable.entity.User;
import services.publics.vulnerable.entity.UserDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController("/test")
public class UserApi {

    @Autowired
    ConversionService conversionService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @PostMapping("/users")
    public CompletableFuture<ResponseEntity<UserDTO>> createUser(
            @RequestBody @Valid UserDTO request
    ) {
        return Mono.just(request)
                .map(userDTO -> conversionService.convert(userDTO, User.class))
                .map(user -> userRepository.save(user))
                .map(user -> conversionService.convert(user, UserDTO.class))
                .map(response -> ResponseEntity.ok(response))
                .toFuture();
    }

    @GetMapping("/users")
    public boolean userExists(@RequestParam("email") String email) {
        return jdbcTemplate.queryForList(String.format("SELECT * FROM users WHERE email LIKE %s", email))
                .isEmpty();
    }

    @GetMapping
    public String redirect(@RequestParam String url) {
        return "redirect:" + url;
    }


}