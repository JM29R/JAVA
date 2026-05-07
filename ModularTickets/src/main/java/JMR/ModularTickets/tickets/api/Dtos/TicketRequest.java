package JMR.ModularTickets.tickets.api.Dtos;

public record TicketRequest(
        String ticket,
        Long  userID
) {
}
