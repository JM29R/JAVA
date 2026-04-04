package JMR.Forum.application.service.UsuarioServiceTest;


import JMR.Forum.application.service.UsuarioService;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceActivoPorIDTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void deberiaRetornarEstadoActivo() {

        Usuario user = new Usuario();
        user.setActivo(true);

        when(usuarioRepository.buscarPorId(1L))
                .thenReturn(Optional.of(user));

        boolean resultado = usuarioService.ActivoporID(1L);

        assertTrue(resultado);
    }

}
