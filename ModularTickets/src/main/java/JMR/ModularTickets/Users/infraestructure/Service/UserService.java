package JMR.ModularTickets.Users.infraestructure.Service;

import JMR.ModularTickets.Users.api.Dtos.UserRequest;
import JMR.ModularTickets.Users.api.Dtos.UserResponse;
import JMR.ModularTickets.Users.domain.Model.Roles;
import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.domain.repository.UserRepository;
import JMR.ModularTickets.Users.infraestructure.persistence.Mapper.UserDTOMapper;
import JMR.ModularTickets.auth.domain.CurrentUserProvider;
import JMR.ModularTickets.auth.domain.PasswordHasher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserDTOMapper DTOmapper;

    private final PasswordHasher hasher;

    private final CurrentUserProvider currentUserProvider;


    private users ActiveUser(){

        String username =
                currentUserProvider.getUsername();

        users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!user.isActivo()){throw new RuntimeException("user is not active");}

        return user;
    }



    public UserResponse create(UserRequest userRequest) {
        if(userRequest == null){return null;}

        users user= DTOmapper.ToDomain(userRequest);
        user.setRol(Roles.USER);
        String pass = hasher.encode(user.getPassword());
        user.setPassword(pass);
        users saved = userRepository.save(user);
        return DTOmapper.ToResponse(saved);

    }

    public List<UserResponse> findAll() {

        users user = ActiveUser();

        List<users> users = userRepository.listUsers();

        return users
                .stream()
                .map(DTOmapper::ToResponse)
                .toList();
    }

    public UserResponse findById(Long id) {

        users verify = ActiveUser();

        users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return DTOmapper.ToResponse(user);
    }

    public UserResponse findByUsername(String username) {

        users verify = ActiveUser();

        users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return DTOmapper.ToResponse(user);
    }

    public UserResponse unban(Long id) {

        users verify = ActiveUser();

        users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(true);
        userRepository.save(user);
        return DTOmapper.ToResponse(user);
    }

    public void delete(Long id){

        users verify = ActiveUser();



        users user= userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(false);

        users deleted = userRepository.save(user);
    }
}
