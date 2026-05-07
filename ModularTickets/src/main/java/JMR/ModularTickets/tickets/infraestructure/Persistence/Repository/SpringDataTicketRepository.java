package JMR.ModularTickets.tickets.infraestructure.Persistence.Repository;

import JMR.ModularTickets.tickets.infraestructure.Persistence.Entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataTicketRepository extends JpaRepository<TicketEntity,Long> {

    Optional<TicketEntity> findByuserId(Long id);
}
