package JMR.Forum.application.service.RespuestaServiceTest;


import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.Infrastructure.persistence.Respuesta.Mapper.RespuestaDTOMapper;
import JMR.Forum.application.service.RespuestaService;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.repository.RespuestaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RespuestaServiceListarporTopicoTest {

    @InjectMocks
    private RespuestaService respuestaService;

    @Mock
    private RespuestaRepository respuestaRepository;

    @Mock
    private RespuestaDTOMapper respuestaDTOMapper;

    @Test
    void deberiaListarRespuestasPorTopico() {

        Long idTopico = 1L;

        Respuesta r1 = new Respuesta();
        Respuesta r2 = new Respuesta();

        RespuestaResponse resp1 = new RespuestaResponse(1L, "msg1", LocalDateTime.now(), 1L, "juan");
        RespuestaResponse resp2 = new RespuestaResponse(2L, "msg2", LocalDateTime.now(), 1L, "juan");

        when(respuestaRepository.buscarTodosPorTopico(idTopico))
                .thenReturn(List.of(r1, r2));

        when(respuestaDTOMapper.toResponse(r1)).thenReturn(resp1);
        when(respuestaDTOMapper.toResponse(r2)).thenReturn(resp2);

        List<RespuestaResponse> resultado = respuestaService.listarPorTopico(idTopico);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(respuestaRepository).buscarTodosPorTopico(idTopico);
    }

    @Test
    void deberiaRetornarListaVaciaSiNoHayRespuestasPorTopico() {

        when(respuestaRepository.buscarTodosPorTopico(any()))
                .thenReturn(List.of());

        List<RespuestaResponse> resultado = respuestaService.listarPorTopico(1L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

}
