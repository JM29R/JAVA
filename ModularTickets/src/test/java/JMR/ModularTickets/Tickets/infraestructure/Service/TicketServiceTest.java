package JMR.ModularTickets.Tickets.infraestructure.Service;

import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.domain.repository.UserRepository;
import JMR.ModularTickets.auth.domain.CurrentUserProvider;
import JMR.ModularTickets.tickets.api.Dtos.TicketRequest;
import JMR.ModularTickets.tickets.api.Dtos.TicketResponse;
import JMR.ModularTickets.tickets.domain.Model.Ticket;
import JMR.ModularTickets.tickets.domain.Repository.TicketRepository;
import JMR.ModularTickets.tickets.infraestructure.Persistence.Mapper.TicketDTOMapper;
import JMR.ModularTickets.tickets.infraestructure.Service.TicketService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @Mock
    private TicketDTOMapper mapper;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    @Nested
    class Create {


        @Test
        void createSuccessfullyTest() {

            // Arrange

            TicketRequest request =
                    new TicketRequest("Problema login");

            users user = new users();
            user.setId(1L);

            Ticket ticket = new Ticket();

            TicketResponse expected =
                    new TicketResponse("Problema login");

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.of(user));

            when(mapper.toDomainDTO(request, 1L))
                    .thenReturn(ticket);

            when(repository.save(ticket))
                    .thenReturn(ticket);

            when(mapper.ticketResponse(ticket))
                    .thenReturn(expected);

            // Act

            TicketResponse result =
                    ticketService.create(request);

            // Assert

            assertNotNull(result);
            assertEquals(expected, result);

            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");
            verify(mapper).toDomainDTO(request, 1L);
            verify(repository).save(ticket);
            verify(mapper).ticketResponse(ticket);
        }

        @Test
        void createUsernotFound(){

            TicketRequest request =
                    new TicketRequest("Problema login");

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.empty());


            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> ticketService.create(request)
            );

            assertEquals("Usuario no encontrado", exception.getMessage());

            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");

            verify(repository, never()).save(any());
            verify(mapper, never()).toDomainDTO(any(), any());
            verify(mapper, never()).ticketResponse(any());



        }

        @Test
        void createInactiveUser(){

            TicketRequest request =
                    new TicketRequest("Problema login");

            users user = new users();
            user.setId(1L);
            user.setActivo(false);

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.of(user));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> ticketService.create(request)
            );

            assertEquals("user is not active", exception.getMessage());

            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");

            verify(repository, never()).save(any());
            verify(mapper, never()).toDomainDTO(any(), any());
            verify(mapper, never()).ticketResponse(any());


        }

    }

    @Nested
    class FindAll {

        @Test
        void findAllTest() {

            users user = new users();
            user.setId(1L);
            user.setActivo(true);

            Ticket ticket = new Ticket();
            ticket.setTicket("Problema login");

            TicketResponse response = new TicketResponse("Problema login");
            List<Ticket> tickets = List.of(ticket);

            when(repository.listTickets())
                    .thenReturn(tickets);

            when(mapper.ticketResponse(ticket))
                    .thenReturn(response);

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.of(user));

            List<TicketResponse> ticketsResult = ticketService.findAll();

            assertNotNull(ticketsResult);
            assertEquals(1, ticketsResult.size());
            assertEquals(response, ticketsResult.get(0));

            verify(repository).listTickets();
            verify(mapper).ticketResponse(ticket);
            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");


        }

    }

    @Nested
    class FindByUserId {

        @Test
        void findByUserIdSuccessfullyTest() {

            Ticket ticket = new Ticket();
            ticket.setTicket("Problema login");

            TicketResponse response = new TicketResponse("Problema login");
            List<Ticket> tickets = List.of(ticket);

            users user = new users();
            user.setId(1L);
            user.setActivo(true);

            when(repository.findByuserId(user.getId()))
                    .thenReturn(tickets);

            when(mapper.ticketResponse(ticket))
                    .thenReturn(response);

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.of(user));

            List<TicketResponse> ticketsResult = ticketService.findByUserId(user.getId());

            assertNotNull(ticketsResult);
            assertEquals(1, ticketsResult.size());
            assertEquals(response, ticketsResult.get(0));

            verify(repository).findByuserId(user.getId());
            verify(mapper).ticketResponse(ticket);
            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");
        }

    }

    @Nested
    class deteleTest{
        @Test
        void deleteSuccessfullyTest() {

            Long id = 1L;

            users user = new users();
            user.setId(1L);
            user.setActivo(true);

            Ticket ticket = new Ticket();
            ticket.setActivo(true);

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.of(user));

            when(repository.findById(id))
                    .thenReturn(Optional.of(ticket));

            when(repository.save(ticket))
                    .thenReturn(ticket);

            ticketService.delete(id);

            assertFalse(ticket.isActivo());

            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");
            verify(repository).findById(id);
            verify(repository).save(ticket);
        }

        @Test
        void deleteNotFoundTest() {

            Long id = 1L;

            users user = new users();
            user.setId(1L);
            user.setActivo(true);

            when(currentUserProvider.getUsername())
                    .thenReturn("juan");

            when(userRepository.findByUsername("juan"))
                    .thenReturn(Optional.of(user));

            when(repository.findById(id))
                    .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> ticketService.delete(id)
            );

            assertEquals("ticket not found", exception.getMessage());

            verify(currentUserProvider).getUsername();
            verify(userRepository).findByUsername("juan");
            verify(repository).findById(id);

            verify(repository, never()).save(any());



        }
    }
}