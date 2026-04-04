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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceBuscarPorIDTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioDTOMapper usuarioDTOMapper;

    @Test
    void deberiaBuscarUsuarioPorId() {

        Usuario user = new Usuario();

        UsuarioResponse response = new UsuarioResponse(1L, "juan","ROLE_USER" ,true);

        when(usuarioRepository.buscarPorId(1L))
                .thenReturn(Optional.of(user));

        when(usuarioDTOMapper.toResponse(user))
                .thenReturn(response);

        UsuarioResponse resultado = usuarioService.BuscarporID(1L);

        assertNotNull(resultado);
    }
}
