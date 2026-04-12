package JMR.Forum.application.service.RespuestaServiceTest;


import JMR.Forum.Infrastructure.Dtos.Request.RespuestaRequest;
import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.Infrastructure.persistence.Respuesta.Mapper.RespuestaDTOMapper;
import JMR.Forum.application.service.RespuestaService;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.RespuestaRepository;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RespuestaServicesCrearTest {

    @InjectMocks
    private RespuestaService respuestaService;

    @Mock
    private RespuestaRepository respuestaRepository;

    @Mock
    private RespuestaDTOMapper respuestaDTOMapper;

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;


        @Test
        void deberiaCrearRespuestaCorrectamente() {

            RespuestaRequest request = new RespuestaRequest("mensaje",2L,3L);

            Usuario usuario = Usuario.builder().id(1L).build();
            Topico topico = new Topico();

            Respuesta respuesta = new Respuesta();
            Respuesta guardada = new Respuesta();
            LocalDateTime fecha = LocalDateTime.now();

            RespuestaResponse response = new RespuestaResponse(1L, "mensaje", fecha, 2L, "juan");

            when(usuarioRepository.buscarPorId(3L))
                    .thenReturn(Optional.of(usuario));

            when(topicoRepository.buscarPorId(2L))
                    .thenReturn(Optional.of(topico));

            when(respuestaDTOMapper.toDomain(request, usuario, topico))
                    .thenReturn(respuesta);

            when(respuestaRepository.guardar(any(Respuesta.class)))
                    .thenReturn(guardada);

            when(respuestaDTOMapper.toResponse(any(Respuesta.class)))
                    .thenReturn(response);

            RespuestaResponse resultado = respuestaService.crear(request);

            assertNotNull(resultado);
            verify(respuestaRepository).guardar(any(Respuesta.class));


        }


    @Test
    void deberiaLanzarErrorSiUsuarioNoExiste() {

        RespuestaRequest request = new RespuestaRequest("mensaje",2L,3L);

        when(usuarioRepository.buscarPorId(3L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            respuestaService.crear(request);
        });

        verify(respuestaRepository, never()).guardar(any());
    }

    @Test
    void deberiaLanzarErrorSiTopicoNoExiste() {

        RespuestaRequest request = new RespuestaRequest( "mensaje",2L,3L);

        Usuario usuario = Usuario.builder().id(3L).build();

        when(usuarioRepository.buscarPorId(3L))
                .thenReturn(Optional.of(usuario));

        when(topicoRepository.buscarPorId(2L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            respuestaService.crear(request);
        });

        verify(respuestaRepository, never()).guardar(any());
    }

    }



