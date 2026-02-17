package JMR.AICHAT.RESERVAS;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosReserva(
        LocalDate fecha,
        LocalTime hora,
        Long canchaId,
        String nombreCliente,
        String telefono

) {
}
