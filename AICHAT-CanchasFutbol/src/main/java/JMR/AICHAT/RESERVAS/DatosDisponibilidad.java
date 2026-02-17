package JMR.AICHAT.RESERVAS;

import java.time.LocalDate;

public record DatosDisponibilidad(
        LocalDate fecha,
        Long canchaId
) {
}
