package JMR.ModularTickets.tickets.infraestructure.Persistence.Repository;

import JMR.ModularTickets.tickets.domain.Model.Ticket;
import JMR.ModularTickets.tickets.domain.Repository.TicketRepository;
import JMR.ModularTickets.tickets.infraestructure.Persistence.Entity.TicketEntity;
import JMR.ModularTickets.tickets.infraestructure.Persistence.Mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class TicketRepositoryJPA implements TicketRepository {

    private final SpringDataTicketRepository repository;
    private final TicketMapper ticketMapper;

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity ticketEntity = ticketMapper.toEntity(ticket);
        TicketEntity savedEntity = repository.save(ticketEntity);
        return ticketMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return repository.findById(id)
                .map(ticketMapper::toDomain);
    }

    @Override
    public List<Ticket> listTickets() {
        return repository.findAll()
                .stream()
                .map(ticketMapper::toDomain)
                .toList();
    }

    @Override
    public List<Ticket> findByuserId(Long id) {
        return repository.findByuserId(id)
                .stream()
                .map(ticketMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
