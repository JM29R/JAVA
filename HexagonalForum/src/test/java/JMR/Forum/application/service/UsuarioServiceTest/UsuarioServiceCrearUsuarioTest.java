package JMR.Forum.application.service.UsuarioServiceTest;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.Infrastructure.persistence.Usuario.Mapper.UsuarioDTOMapper;
import JMR.Forum.application.service.UsuarioService;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceCrearUsuarioTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioDTOMapper usuarioDTOMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void deberiaCrearUsuarioCorrectamente() {

        UsuarioRequest request = new UsuarioRequest("juan", "1234");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setNombre("juan");
        usuarioGuardado.setActivo(true);
        usuarioGuardado.setRol(Roles.ROLE_USER);

        UsuarioResponse response = new UsuarioResponse(1L, "juan", "ROLE_USER",true);

        when(passwordEncoder.encode("1234")).thenReturn("1234_encriptada");
        when(usuarioRepository.guardar(any(Usuario.class))).thenReturn(usuarioGuardado);
        when(usuarioDTOMapper.toResponse(usuarioGuardado)).thenReturn(response);

        UsuarioResponse resultado = usuarioService.crearUsuario(request);

        assertNotNull(resultado);
        assertEquals("juan", resultado.nombre());

        verify(passwordEncoder).encode("1234");
        verify(usuarioRepository).guardar(any(Usuario.class));
    }

}
