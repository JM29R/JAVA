package JMR.Forum.infrastructure.Controller.TopicoControllerTest;

import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TopicoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TopicoControllerCrearTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicoService topicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;


    @Test
    void deberiaCrearTopicoCorrectamente() throws Exception {

        TopicoRequest request = new TopicoRequest("titulo", "mensaje", 1L);

        TopicoResponse response = new TopicoResponse(
                1L,
                "titulo",
                "mensaje",
                2L

        );

        when(topicoService.crearTopico(any())).thenReturn(response);

        mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("titulo"))
                .andExpect(jsonPath("$.mensaje").value("mensaje"))
                .andExpect(jsonPath("$.idAutor").value(2));
    }
}