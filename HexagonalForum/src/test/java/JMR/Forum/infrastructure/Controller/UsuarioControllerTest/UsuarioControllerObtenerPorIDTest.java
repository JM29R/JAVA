package JMR.Forum.infrastructure.Controller.UsuarioControllerTest;

import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.Infrastructure.controller.UsuarioController;
import JMR.Forum.application.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerObtenerPorIDTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaObtenerUsuarioPorId() throws Exception {

        UsuarioResponse response = new UsuarioResponse(1L, "juan", "ROLE_USER",true);

        when(usuarioService.BuscarporID(1L)).thenReturn(response);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("juan"));
    }

}