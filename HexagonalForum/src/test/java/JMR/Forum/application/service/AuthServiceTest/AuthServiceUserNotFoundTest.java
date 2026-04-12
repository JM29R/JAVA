package JMR.Forum.application.service.AuthServiceTest;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioLoginRequest;
import JMR.Forum.application.service.AuthService;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUserNotFoundTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;



    @Test
    void deberiaLanzarErrorSiUsuarioNoExiste() {

        UsuarioLoginRequest request = new UsuarioLoginRequest("juan", "1234");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(usuarioRepository.findByNombre("juan"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
    }
}
