package JMR.Forum.infrastructure.Controller.TopicoControllerTest;


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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TopicoController.class)
@AutoConfigureMockMvc(addFilters = false)
class TopicoControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicoService topicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaEliminarTopico() throws Exception {

        doNothing().when(topicoService).eliminar(1L);

        mockMvc.perform(delete("/topicos/1")
                        .with(user("juan"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(topicoService).eliminar(1L);
    }

}