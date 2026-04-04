package JMR.Forum.application.service.TopicoServiceTest;


import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.application.service.TopicoService;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopicoServiceBuscarTodosTest {


    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private TopicoDTOMapper topicoMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TopicoService topicoService;

    @Test
    void deberiaRetornarListaDeTopicos() {

        Topico topico = new Topico();

        List<Topico> lista = List.of(topico);

        TopicoResponse response = new TopicoResponse(1L, "titulo", "mensaje", 1L);

        when(topicoRepository.buscarTodos()).thenReturn(lista);
        when(topicoMapper.toResponse(topico)).thenReturn(response);

        List<TopicoResponse> resultado = topicoService.buscarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());

        verify(topicoRepository).buscarTodos();
    }
}
