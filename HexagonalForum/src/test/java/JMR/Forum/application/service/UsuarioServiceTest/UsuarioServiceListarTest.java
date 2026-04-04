package JMR.Forum.application.service.UsuarioServiceTest;


import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.Infrastructure.persistence.Usuario.Mapper.UsuarioDTOMapper;
import JMR.Forum.application.service.UsuarioService;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceListarTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioDTOMapper usuarioDTOMapper;

    @Test
    void deberiaListarUsuarios() {

        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();

        when(usuarioRepository.listar())
                .thenReturn(List.of(u1, u2));

        when(usuarioDTOMapper.toResponse(any()))
                .thenReturn(new UsuarioResponse(1L, "juan","ROLE_USER" ,true));

        List<UsuarioResponse> resultado = usuarioService.listar();

        assertEquals(2, resultado.size());
    }

}
