package JMR.ModularTickets.auth.domain.Service;

import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.domain.repository.UserRepository;
import JMR.ModularTickets.auth.api.Dtos.AuthRequest;
import JMR.ModularTickets.auth.api.Dtos.AuthResponse;
import JMR.ModularTickets.auth.infraestructure.jwt.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JWTService jwtService;


    public AuthResponse login(AuthRequest authRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password()
        );
        authenticationManager.authenticate(authToken);

        users user = userRepository.findByUsername(authRequest.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwt = jwtService.generateToken(user.getUsername(), user.getRol().name());

        return new AuthResponse(jwt);

    }




}
