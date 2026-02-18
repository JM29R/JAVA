package JMR.AICHAT.DTOs.Inputs;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosModificarReservaRequest(
       //fechas viejas
        String telefono,
        LocalDate fechaActual,
        LocalTime horaActual,
        //fechas nuevas
        LocalDate fechaNueva,
        LocalTime horaNueva,
        Long canchaId,
        String nombreClient
) {
}
