package JMR.AICHAT.DTOs.Inputs;

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
