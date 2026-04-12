package JMR.Forum.application.service.UsuarioServiceTest;

import JMR.Forum.Infrastructure.Dtos.Request.UsuarioLoginRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceEditarUsuarioTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioDTOMapper usuarioDTOMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void deberiaEditarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setNombre("viejo");

        UsuarioLoginRequest request = new UsuarioLoginRequest("nuevo", "1234");

        Usuario actualizado = new Usuario();
        actualizado.setNombre("nuevo");

        UsuarioResponse response = new UsuarioResponse(1L, "nuevo","ROLE_USER" ,true);

        when(usuarioRepository.buscarPorId(1L))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.encode("1234"))
                .thenReturn("pass_encriptada");

        when(usuarioRepository.guardar(usuario))
                .thenReturn(actualizado);

        when(usuarioDTOMapper.toResponse(actualizado))
                .thenReturn(response);

        UsuarioResponse resultado = usuarioService.editarUsuario(1L, request);

        assertEquals("nuevo", resultado.nombre());
    }
}
