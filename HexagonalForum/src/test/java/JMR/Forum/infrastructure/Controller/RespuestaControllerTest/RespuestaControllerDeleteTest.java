package JMR.Forum.infrastructure.Controller.RespuestaControllerTest;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RespuestaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RespuestaControllerDeleteTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RespuestaService respuestaService;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaEliminarRespuestaCorrectamente() throws Exception {

        doNothing().when(respuestaService)
                .eliminar(eq(1L), any(UsuarioRequest.class));

        mockMvc.perform(delete("/respuestas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "nombre": "juan"
                        }
                    """))
                .andExpect(status().isNoContent());
    }
}
