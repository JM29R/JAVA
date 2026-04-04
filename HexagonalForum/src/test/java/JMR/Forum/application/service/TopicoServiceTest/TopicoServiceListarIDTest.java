package JMR.Forum.application.service.TopicoServiceTest;

import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.application.service.TopicoService;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.repository.TopicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopicoServiceListarIDTest {

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private TopicoDTOMapper topicoMapper;

    @InjectMocks
    private TopicoService topicoService;


    @Test
    void deberiaRetornarTopicoPorId() {

        Long id = 1L;

        Topico topico = new Topico();
        TopicoResponse response = new TopicoResponse(1L, "titulo", "mensaje", 1L);

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.of(topico));

        when(topicoMapper.toResponse(topico))
                .thenReturn(response);

        TopicoResponse resultado = topicoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("titulo", resultado.titulo());

        verify(topicoRepository).buscarPorId(id);
    }

    @Test
    void deberiaLanzarErrorSiTopicoNoExiste() {

        Long id = 1L;

        when(topicoRepository.buscarPorId(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            topicoService.buscarPorId(id);
        });
    }
}
