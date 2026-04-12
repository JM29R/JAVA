package JMR.Forum.infrastructure.Controller.AuthControllerTest;

import JMR.Forum.Infrastructure.Dtos.Response.JWTResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.Infrastructure.controller.AuthController;
import JMR.Forum.application.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Test
    void deberiaRetornar200CuandoLoginEsCorrecto() throws Exception {

        JWTResponse response = new JWTResponse("token123",1L);

        when(authService.login(any()))
                .thenReturn(response);

        String json = """
        {
            "nombre": "juan",
            "password": "1234"
        }
    """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.JWT").value("token123"));
    }
}
