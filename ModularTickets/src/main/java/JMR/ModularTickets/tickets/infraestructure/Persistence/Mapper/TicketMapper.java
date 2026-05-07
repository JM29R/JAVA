package JMR.ModularTickets.tickets.infraestructure.Persistence.Mapper;


import JMR.ModularTickets.tickets.domain.Model.Ticket;
import JMR.ModularTickets.tickets.infraestructure.Persistence.Entity.TicketEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketMapper {

    public Ticket toDomain(TicketEntity ticketEntity) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketEntity.getId());
        ticket.setTicket(ticketEntity.getTicket());
        ticket.setUserId(ticketEntity.getUserId());
        ticket.setActivo(ticketEntity.getActivo());


        ticket.setCreatedAt(ticketEntity.getCreatedAt());
        ticket.setUpdatedAt(ticketEntity.getUpdatedAt());
        return ticket;
    }

    public TicketEntity toEntity(Ticket ticket) {
        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(ticket.getId());
        ticketEntity.setTicket(ticket.getTicket());
        ticketEntity.setUserId(ticket.getUserId());
        ticketEntity.setActivo(ticket.isActivo());

        ticketEntity.setCreatedAt(ticket.getCreatedAt());
        ticketEntity.setUpdatedAt(ticket.getUpdatedAt());

        return ticketEntity;
    }

}
