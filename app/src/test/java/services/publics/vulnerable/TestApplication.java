package services.publics.vulnerable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication extends IntegrationTest {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

