package JMR.ModularTickets.tickets.domain.Repository;

import JMR.ModularTickets.tickets.domain.Model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Optional<Ticket> findById(Long id);

    List<Ticket> listTickets();

    List<Ticket> findByuserId(Long id);

    void delete(Long id);

}
