package JMR.Forum.infrastructure.Controller.TopicoControllerTest;

import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.Infrastructure.controller.TopicoController;
import JMR.Forum.application.service.TopicoService;
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

@WebMvcTest(TopicoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TopicoControllerObtenerPorIDTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicoService topicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaObtenerTopicoPorId() throws Exception {

        TopicoResponse response = new TopicoResponse(
                1L,
                "titulo",
                "mensaje",
                2L
        );

        when(topicoService.buscarPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/topicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("titulo"));
    }

}