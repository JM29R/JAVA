package JMR.Forum.application.service.TopicoServiceTest;

import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.persistence.Topico.Mapper.TopicoDTOMapper;
import JMR.Forum.application.service.TopicoService;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.TopicoRepository;
import JMR.Forum.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TopicoServiceCrearTest {

    @Mock
    private TopicoRepository topicoRepository;

    @Mock
    private TopicoDTOMapper topicoMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TopicoService topicoService;

    private static final String USERNAME = "juan";

    @BeforeEach
    void setUp() {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(USERNAME, null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void deberiaCrearTopicoCorrectamente() {


        TopicoRequest request = new TopicoRequest("titulo", "mensaje");

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("juan")
                .rol(Roles.ROLE_USER)
                .password("1234")
                .build();

        Topico topico = new Topico();
        Topico topicoGuardado = new Topico();

        topico.setAutor(usuario);

        TopicoResponse response = new TopicoResponse(1L, "titulo", "mensaje",1L);

        when(usuarioRepository.findByNombre("juan"))
                .thenReturn(Optional.of(usuario));

        when(topicoMapper.toDomain(request))
                .thenReturn(topico);

        when(topicoRepository.guardar(topico))
                .thenReturn(topicoGuardado);

        when(topicoMapper.toResponse(topicoGuardado))
                .thenReturn(response);


        TopicoResponse resultado = topicoService.crearTopico(request);


        assertNotNull(resultado);
        assertEquals("titulo", resultado.titulo());


        verify(usuarioRepository).findByNombre("juan");
        verify(topicoRepository).guardar(topico);
        verify(topicoMapper).toResponse(topicoGuardado);
    }

}
