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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceEliminarUsuarioTest {


    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioDTOMapper usuarioDTOMapper;



    @Test
    void deberiaDesactivarUsuario() {

        Usuario user = new Usuario();
        user.setActivo(true);

        UsuarioResponse response = new UsuarioResponse(1L, "juan","ROLE_USER" ,false);

        when(usuarioRepository.buscarPorId(1L))
                .thenReturn(Optional.of(user));

        when(usuarioRepository.guardar(user))
                .thenReturn(user);

        when(usuarioDTOMapper.toResponse(user))
                .thenReturn(response);

        UsuarioResponse resultado = usuarioService.eliminarUsuario(1L);

        assertFalse(user.isActivo());
        verify(usuarioRepository).guardar(user);
    }

}
