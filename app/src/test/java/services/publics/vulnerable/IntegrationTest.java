package services.publics.vulnerable;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;

import java.util.Map;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int localServerPort;

    protected RequestSpecification requestSpecification;

    static {
        var mysql = new MySQLContainer("mysql:5.6")
                .withDatabaseName("test")
                .withInitScript("database/init_schema.sql");
        mysql.start();

        System.getProperties().putAll(Map.of(
                "spring.datasource.url", mysql.getJdbcUrl(),
                "spring.datasource.username", mysql.getUsername(),
                "spring.datasource.password", mysql.getPassword()
        ));
    }

    @BeforeEach
    public void init() {
        requestSpecification = new RequestSpecBuilder().setPort(localServerPort).build();
    }

}
