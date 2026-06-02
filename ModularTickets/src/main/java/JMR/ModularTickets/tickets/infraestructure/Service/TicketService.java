package JMR.ModularTickets.tickets.infraestructure.Service;


import JMR.ModularTickets.Users.domain.Model.users;
import JMR.ModularTickets.Users.domain.repository.UserRepository;
import JMR.ModularTickets.auth.domain.CurrentUserProvider;
import JMR.ModularTickets.tickets.api.Dtos.TicketRequest;
import JMR.ModularTickets.tickets.api.Dtos.TicketResponse;
import JMR.ModularTickets.tickets.domain.Model.Ticket;
import JMR.ModularTickets.tickets.domain.Repository.TicketRepository;
import JMR.ModularTickets.tickets.infraestructure.Persistence.Mapper.TicketDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;

    private final TicketDTOMapper mapper;

    private final CurrentUserProvider currentUserProvider;

    private final UserRepository userRepository;

    private users ActiveUser(){

        String username =
                currentUserProvider.getUsername();

        users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!user.isActivo()){throw new RuntimeException("user is not active");}

        return user;
    }


    public TicketResponse create(TicketRequest ticketRequest) {
        users user = ActiveUser();

        Ticket ticket = mapper.toDomainDTO(ticketRequest,user.getId());
        Ticket ticketSaved = repository.save(ticket);
        return mapper.ticketResponse(ticketSaved);
    }

    public List<TicketResponse> findAll() {

        users user = ActiveUser();

        List<Ticket> list = repository.listTickets();
        if (list == null) {return null;}
        return list
                .stream()
                .map(mapper::ticketResponse)
                .toList();
    }

    public List<TicketResponse> findByUserId(Long id){

        users user = ActiveUser();

        List<Ticket> list = repository.findByuserId(id);
        if (list == null) {return null;}
        return list
                .stream()
                .map(mapper::ticketResponse)
                .toList();
    }

    public void delete(Long id) {

        users user = ActiveUser();

        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ticket not found"));
        ticket.setActivo(false);
        repository.save(ticket);
    }



}
