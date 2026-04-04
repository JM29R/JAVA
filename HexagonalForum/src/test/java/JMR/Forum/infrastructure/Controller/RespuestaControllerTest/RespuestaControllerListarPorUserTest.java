package JMR.Forum.infrastructure.Controller.RespuestaControllerTest;


import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.Infrastructure.controller.RespuestaController;
import JMR.Forum.application.service.RespuestaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RespuestaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RespuestaControllerListarPorUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RespuestaService respuestaService;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaListarRespuestasPorUsuario() throws Exception {

        RespuestaResponse r1 = new RespuestaResponse(
                1L,
                "respuesta user",
                LocalDateTime.now(),
                2L,
                "juan"
        );

        when(respuestaService.listarPorUsuario(1L))
                .thenReturn(List.of(r1));

        mockMvc.perform(get("/respuestas/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contenido").value("respuesta user"));
    }
}
