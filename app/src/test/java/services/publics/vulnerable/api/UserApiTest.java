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
        UserDTO userDTO = new UserDTO(UUID.randomUUID().toString(), "dave@david.d");

        UserDTO userDTOResponse = given()
                .when()
                .contentType("application/json")
                .body(userDTO)
                .post("/users")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .get();

        assertThat(userDTOResponse).isEqualTo(userDTO);
    }

    @Test
    public void shouldReturnUser() {
        UserDTO userDTO = new UserDTO(UUID.randomUUID().toString(), "dave@david.d");

        List<UserDTO> userDTOResponse = given()
                .get("/users?dave@")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .get();

        assertThat(userDTOResponse).isEqualTo(userDTO);
    }
}
