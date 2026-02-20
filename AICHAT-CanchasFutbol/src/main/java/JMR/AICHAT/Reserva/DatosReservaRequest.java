package JMR.AICHAT.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosReservaRequest(
        LocalDate fecha,
        LocalTime hora,
        Long canchaId,
        String nombreCliente,
        String telefono

) {
}
