package services.publics.vulnerable.api;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.MySQLContainer;
import services.publics.vulnerable.IntegrationTest;
import services.publics.vulnerable.db.UserRepository;
import services.publics.vulnerable.entity.User;
import services.publics.vulnerable.entity.UserDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UserApiTest extends IntegrationTest {

    @Autowired
    UserRepository repository;

    @Test
    public void shouldCreateUser() {
        List<?> userDTOResponse = given(requestSpecification)
                .param("email", "dave@")
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        assertThat(userDTOResponse).hasSize(1);
    }

    @Test
    public void shouldReturnUser() {
        String id = UUID.randomUUID().toString();
        String email = "dave@david.d";

        repository.save(new User(id, email, "password"));

        List<?> userDTOResponse = given(requestSpecification)
                .param("email", "dave@")
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        assertThat(userDTOResponse).hasSize(1);
    }
}
