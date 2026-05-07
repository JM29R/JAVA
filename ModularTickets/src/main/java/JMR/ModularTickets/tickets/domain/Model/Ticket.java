package JMR.ModularTickets.tickets.domain.Model;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    private Long id;
    private String ticket;
    private Long userId;

    @Builder.Default
    private boolean activo = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}
