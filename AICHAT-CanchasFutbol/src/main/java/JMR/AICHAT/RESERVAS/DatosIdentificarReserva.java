package JMR.AICHAT.RESERVAS;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosIdentificarReserva(
        LocalDate fecha,
        LocalTime hora,
        String telefono
) {
}
