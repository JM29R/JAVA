package JMR.AICHAT.Reserva.ReservaAI;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosModificarReservaAI(
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
