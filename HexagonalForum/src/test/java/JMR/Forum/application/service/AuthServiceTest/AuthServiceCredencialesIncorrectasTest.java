package JMR.Forum.application.service.AuthServiceTest;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.application.service.AuthService;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceCredencialesIncorrectasTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void deberiaLanzarErrorSiCredencialesInvalidas() {

        UsuarioRequest request = new UsuarioRequest("juan", "mal");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Email o contraseña incorrectos", ex.getMessage());
    }

}
