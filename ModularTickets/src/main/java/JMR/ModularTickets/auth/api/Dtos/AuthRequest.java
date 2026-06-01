package JMR.ModularTickets.auth.api.Dtos;

import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record AuthRequest (
        @NotBlank
        String username,
        @NotBlank
        String password
){
}
