package JMR.Forum.infrastructure.Controller.RespuestaControllerTest;


import JMR.Forum.Infrastructure.Dtos.Request.RespuestaRequest;
import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.Infrastructure.controller.RespuestaController;
import JMR.Forum.application.service.RespuestaService;
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

@WebMvcTest(RespuestaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RespuestaContollerCrearTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RespuestaService respuestaService;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaCrearRespuestaCorrectamente() throws Exception {

        RespuestaResponse response = new RespuestaResponse(
                1L,
                "mensaje",
                LocalDateTime.now(),
                2L,
                "juan"
        );

        when(respuestaService.crear(any(RespuestaRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/respuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "autorId": 1,
                            "mensaje": "mensaje",
                            "topicoId": 2
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenido").value("mensaje"));
    }

}
