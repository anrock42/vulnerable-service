package services.publics.vulnerable.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import services.publics.vulnerable.IntegrationTest;
import services.publics.vulnerable.entity.User;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MySQLTest extends IntegrationTest {

    @Autowired
    UserRepository repository;

    @Test
    public void dbConnectionTest() {
        User dave = User.builder()
                .id(UUID.randomUUID().toString())
                .email("dave@exmaple.com")
                .password("daveIsCool")
                .build();

        repository.save(dave);

        List<User> result = (List<User>) repository.findAll();

        assertThat(result).isNotEmpty();
    }
}
