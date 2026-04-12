package JMR.Forum.application.service.TopicoServiceTest;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import JMR.Forum.application.service.TopicoService;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;




@ExtendWith(MockitoExtension.class)
public class TopicoServiceEliminarTest {

    @Mock
    private TopicoRepository topicoRepository;


    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TopicoService topicoService;

    private void mockSecurityContext(String username) {
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void deberiaEliminarTopicoSiEsAutor() {

        Long id = 1L;

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("juan")
                .rol(Roles.ROLE_USER)
                .build();

        Topico topico = new Topico();
        topico.setAutor(usuario);

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.of(topico));

        when(usuarioRepository.findByNombre("juan"))
                .thenReturn(Optional.of(usuario));

        mockSecurityContext("juan");

        topicoService.eliminar(id);

        verify(topicoRepository).eliminar(id);
    }

    @Test
    void deberiaLanzarErrorSiTopicoNoExiste() {

        when(topicoRepository.buscarPorId(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            topicoService.eliminar(1L);
        });
    }

    @Test
    void deberiaLanzarErrorSiUsuarioNoExiste() {

        Long id = 1L;

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            topicoService.eliminar(id);
        });

        verify(topicoRepository, never()).eliminar(any());
    }

    @Test
    void noDeberiaEliminarSiNoTienePermiso() {

        Long id = 1L;

        Usuario autor = Usuario.builder()
                .id(1L)
                .nombre("juan")
                .rol(Roles.ROLE_USER)
                .build();

        Usuario otro = Usuario.builder()
                .id(2L)
                .nombre("pedro")
                .rol(Roles.ROLE_USER)
                .build();

        Topico topico = new Topico();
        topico.setAutor(autor);

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.of(topico));

        when(usuarioRepository.findByNombre("pedro"))
                .thenReturn(Optional.of(otro));

        mockSecurityContext("pedro");

        assertThrows(RuntimeException.class, () -> {
            topicoService.eliminar(id);
        });

        verify(topicoRepository, never()).eliminar(any());
    }


}
