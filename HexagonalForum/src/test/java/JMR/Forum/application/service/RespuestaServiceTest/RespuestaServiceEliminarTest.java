package JMR.Forum.application.service.RespuestaServiceTest;



import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.application.service.RespuestaService;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.RespuestaRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RespuestaServiceEliminarTest {

    @InjectMocks
    private RespuestaService respuestaService;

    @Mock
    private RespuestaRepository respuestaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

@Test
    void deberiaEliminarRespuestaCorrectamente() {
    Long id = 1L;

    Usuario autor = Usuario.builder()
            .id(1L)
            .rol(Roles.ROLE_USER)
            .build();

    Topico topico = new Topico();
    topico.setAutor(Usuario.builder().id(99L).build());

    Respuesta respuesta = new Respuesta();
    respuesta.setAutor(autor);
    respuesta.setTopico(topico);

    UsuarioRequest request = new UsuarioRequest("juan","123456");

    when(respuestaRepository.buscarPorId(id))
            .thenReturn(Optional.of(respuesta));

    when(usuarioRepository.findByNombre("juan"))
            .thenReturn(Optional.of(autor));

    respuestaService.eliminar(id, request);

    verify(respuestaRepository).borrar(respuesta);

}

    @Test
    void deberiaEliminarSiEsAdmin() {

        Usuario admin = Usuario.builder()
                .id(2L)
                .rol(Roles.ROLE_ADMIN)
                .build();

        Respuesta respuesta = new Respuesta();
        respuesta.setAutor(Usuario.builder().id(1L).build());
        respuesta.setTopico(new Topico());

        when(respuestaRepository.buscarPorId(any()))
                .thenReturn(Optional.of(respuesta));

        when(usuarioRepository.findByNombre(any()))
                .thenReturn(Optional.of(admin));

        respuestaService.eliminar(1L, new UsuarioRequest("admin","admin"));

        verify(respuestaRepository).borrar(respuesta);
    }

    @Test
    void deberiaEliminarSiEsAutorDelTopico() {

        Usuario user = Usuario.builder()
                .id(3L)
                .rol(Roles.ROLE_USER)
                .build();

        Topico topico = new Topico();
        topico.setAutor(user);

        Respuesta respuesta = new Respuesta();
        respuesta.setAutor(Usuario.builder().id(1L).build());
        respuesta.setTopico(topico);

        when(respuestaRepository.buscarPorId(any()))
                .thenReturn(Optional.of(respuesta));

        when(usuarioRepository.findByNombre(any()))
                .thenReturn(Optional.of(user));

        respuestaService.eliminar(1L, new UsuarioRequest("user","user"));

        verify(respuestaRepository).borrar(respuesta);
    }

    @Test
    void noDeberiaEliminarSiNoTienePermiso() {

        Usuario user = Usuario.builder()
                .id(5L)
                .rol(Roles.ROLE_USER)
                .build();

        Topico topico = new Topico();
        topico.setAutor(Usuario.builder().id(3L).build());

        Respuesta respuesta = new Respuesta();
        respuesta.setAutor(Usuario.builder().id(1L).build());
        respuesta.setTopico(topico);

        when(respuestaRepository.buscarPorId(any()))
                .thenReturn(Optional.of(respuesta));

        when(usuarioRepository.findByNombre(any()))
                .thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> {
            respuestaService.eliminar(1L, new UsuarioRequest("user","user"));
        });

        verify(respuestaRepository, never()).borrar(any());
    }

}
