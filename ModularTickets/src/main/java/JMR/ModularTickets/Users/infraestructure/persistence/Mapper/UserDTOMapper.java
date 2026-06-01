package JMR.ModularTickets.Users.infraestructure.persistence.Mapper;


import JMR.ModularTickets.Users.api.Dtos.UserRequest;
import JMR.ModularTickets.Users.api.Dtos.UserResponse;
import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
;

@AllArgsConstructor
@Component
public class UserDTOMapper {

    private final UserRepository userRepository;



    public users ToDomain(UserRequest userRequest) {
        if (userRequest == null) { return null; }

        users user = new users();
        user.setUsername(userRequest.username());
        user.setPassword(userRequest.password());
        return user;
    }

    public UserResponse ToResponse(users user) {
        UserResponse userResponse = new UserResponse(
                user.getUsername(),
                user.getId()
        );
        return userResponse;
    }

}
