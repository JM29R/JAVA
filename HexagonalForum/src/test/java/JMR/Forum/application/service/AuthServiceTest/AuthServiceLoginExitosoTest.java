package JMR.Forum.application.service.AuthServiceTest;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.JWTResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.application.service.AuthService;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthServiceLoginExitosoTest {


    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Test
    void deberiaLoguearCorrectamente() {

        UsuarioRequest request = new UsuarioRequest("juan", "1234");

        Usuario usuario = Usuario.builder()
                .nombre("juan")
                .rol(Roles.ROLE_USER)
                .build();

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(usuarioRepository.findByNombre("juan"))
                .thenReturn(Optional.of(usuario));

        when(jwtService.generateToken("juan", "ROLE_USER"))
                .thenReturn("token123");

        JWTResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token123", response.JWT());
    }

}
