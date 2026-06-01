package JMR.ModularTickets.tickets.api.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TicketRequest(
        @NotBlank @Size(min = 1, max = 150)
        String ticket
) {
}
