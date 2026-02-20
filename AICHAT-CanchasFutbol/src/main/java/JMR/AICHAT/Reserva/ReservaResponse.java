package JMR.AICHAT.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaResponse(
        Long id,
        LocalDate fecha,
        LocalTime hora,
        String nombreCliente,
        String telefono,
        Long canchaId

) {
}
