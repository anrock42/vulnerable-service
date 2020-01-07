package services.publics.vulnerable.services;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import services.publics.vulnerable.entity.User;
import services.publics.vulnerable.entity.UserDTO;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User value) {
        return new UserDTO(value.getId(), value.getEmail());
    }
}
