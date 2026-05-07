package JMR.ModularTickets.tickets.infraestructure.Persistence.Mapper;


import JMR.ModularTickets.tickets.api.Dtos.TicketRequest;
import JMR.ModularTickets.tickets.api.Dtos.TicketResponse;
import JMR.ModularTickets.tickets.domain.Model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketDTOMapper {

    public Ticket toDomainDTO(TicketRequest request) {

        return Ticket.builder()
                .ticket(request.ticket())
                .userId(request.userID())
                .build();
    }

    public TicketResponse ticketResponse(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return new TicketResponse(
                ticket.getTicket()
        );
    }

}
