package JMR.ModularTickets.tickets.api.Controller;


import JMR.ModularTickets.tickets.api.Dtos.TicketRequest;
import JMR.ModularTickets.tickets.api.Dtos.TicketResponse;
import JMR.ModularTickets.tickets.domain.Model.Ticket;
import JMR.ModularTickets.tickets.infraestructure.Persistence.Mapper.TicketDTOMapper;
import JMR.ModularTickets.tickets.infraestructure.Service.TicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {


    private final TicketService service;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody @Valid TicketRequest ticket) {

        return ResponseEntity.ok(service.create(ticket));

    }



    @GetMapping("/list")
    public ResponseEntity<List<TicketResponse>> listTickets() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TicketResponse>> listTicketsByuserId(@PathVariable @Min(1) Long id) {

        return ResponseEntity.ok(service.findByUserId(id));

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Delete(@PathVariable @Min(1) Long id){

        service.delete(id);
        return ResponseEntity.ok().build();

    }







}
