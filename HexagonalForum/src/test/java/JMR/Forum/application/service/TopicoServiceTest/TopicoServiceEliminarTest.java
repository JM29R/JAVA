package JMR.Forum.application.service.TopicoServiceTest;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.application.service.TopicoService;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicoServiceEliminarTest {

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private TopicoDTOMapper topicoMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TopicoService topicoService;

    @Test
    void deberiaEliminarTopicoSiEsAutor() {

        // ARRANGE
        Long id = 1L;

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("juan")
                .build();

        Topico topico = new Topico();
        topico.setAutor(usuario);

        UsuarioRequest request = new UsuarioRequest("juan","123456");

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.of(topico));

        when(usuarioRepository.findByNombre("juan"))
                .thenReturn(Optional.of(usuario));

        // ACT
        topicoService.eliminar(id, request);

        // ASSERT
        verify(topicoRepository).eliminar(id);
    }

    @Test
    void deberiaLanzarErrorSiTopicoNoExiste() {

        Long id = 1L;
        UsuarioRequest request = new UsuarioRequest("juan","123456");

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            topicoService.eliminar(id, request);
        });

        verify(topicoRepository, never()).eliminar(any());
    }

    @Test
    void deberiaLanzarErrorSiUsuarioNoExiste() {

        Long id = 1L;

        Topico topico = new Topico();

        UsuarioRequest request = new UsuarioRequest("juan","123456");

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.of(topico));

        when(usuarioRepository.findByNombre("juan"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            topicoService.eliminar(id, request);
        });

        verify(topicoRepository, never()).eliminar(any());
    }

    @Test
    void noDeberiaEliminarSiNoTienePermiso() {

        Long id = 1L;

        Usuario autor = Usuario.builder()
                .id(1L)
                .nombre("juan")
                .build();

        Usuario otro = Usuario.builder()
                .id(2L)
                .nombre("pedro")
                .build();

        Topico topico = new Topico();
        topico.setAutor(autor);

        UsuarioRequest request = new UsuarioRequest("pedro","123456");

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.of(topico));

        when(usuarioRepository.findByNombre("pedro"))
                .thenReturn(Optional.of(otro));

        assertThrows(RuntimeException.class, () -> {
            topicoService.eliminar(id, request);
        });

        verify(topicoRepository, never()).eliminar(any());
    }


}
