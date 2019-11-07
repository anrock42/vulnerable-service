package services.publics.vulnerable.db;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import services.publics.vulnerable.entity.User;

import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String id);
}
