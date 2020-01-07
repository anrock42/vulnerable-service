package services.publics.vulnerable.api;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
import services.publics.vulnerable.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class UserApi {

    ConversionService conversionService;

    UserService userService;

    @PostMapping
    public Mono<ResponseEntity<UserDTO>> createUser(
            @RequestBody @Valid UserDTO request
    ) {
        return Mono.just(request)
                .map(userDTO -> conversionService.convert(userDTO, User.class))
                .map(userService::save)
                .map(user -> conversionService.convert(user, UserDTO.class))
                .map(response -> ResponseEntity.ok(response));
    }

    @GetMapping
    public List<UserDTO> findUser(@RequestParam("email") String email) {
        // CLRF attack
        log.info("Received GET:{}", email);
        if(! email.contains("@")) {
            // Reflected XSS
            throw new IllegalArgumentException(String.format("Email %s does not look like an email!", email));
        }

        // stored XSS
        return userService.findUsers(email)
                .stream()
                .map(user -> conversionService.convert(user, UserDTO.class))
                .collect(Collectors.toList());
    }


}