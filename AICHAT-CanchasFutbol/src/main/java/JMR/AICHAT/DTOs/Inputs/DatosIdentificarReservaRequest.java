package JMR.AICHAT.DTOs.Inputs;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosIdentificarReservaRequest(
        LocalDate fecha,
        LocalTime hora,
        String telefono
) {
}
