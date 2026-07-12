package JMR.ModularTickets.Tickets.api.Controller;

import JMR.ModularTickets.auth.infraestructure.jwt.JWTService;
import JMR.ModularTickets.auth.infraestructure.security.CustomUserDetailsService;
import JMR.ModularTickets.auth.infraestructure.security.JwtAuthenticationfilter;
import JMR.ModularTickets.tickets.api.Controller.TicketController;
import JMR.ModularTickets.tickets.api.Dtos.TicketRequest;
import JMR.ModularTickets.tickets.api.Dtos.TicketResponse;
import JMR.ModularTickets.tickets.infraestructure.Service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest (TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService service;


    @MockitoBean
    private JwtAuthenticationfilter jwtAuthenticationfilter;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

@Nested
class CreateTicket{
    @Test
    void createSuccessfullyTicket() throws Exception {


        // Arrange
        TicketRequest request = new TicketRequest("Problema con login");

        TicketResponse response = new TicketResponse("Problema con login");

        when(service.create(any(TicketRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/ticket/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket")
                        .value("Problema con login"));

        verify(service).create(any(TicketRequest.class));

    }

    @Test
    void createBlankTicket() throws Exception {
        // Arrange
        TicketRequest request = new TicketRequest("");

        // Act & Assert
        mockMvc.perform(post("/ticket/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }


}

    @Test
    void listTickets() {


    }

    @Test
    void listTicketsByuserId() {
    }

    @Test
    void delete() {
    }
}