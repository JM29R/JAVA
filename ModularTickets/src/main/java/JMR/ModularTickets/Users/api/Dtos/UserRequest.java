package JMR.ModularTickets.Users.api.Dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
