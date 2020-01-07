package services.publics.vulnerable.services;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import services.publics.vulnerable.entity.User;
import services.publics.vulnerable.entity.UserDTO;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {
    @Override
    public User convert(UserDTO value) {
        return new User(value.getId(), value.getEmail(), "password");
    }
}
