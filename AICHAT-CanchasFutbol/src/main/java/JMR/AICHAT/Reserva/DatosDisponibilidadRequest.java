package JMR.AICHAT.Reserva;

import java.time.LocalDate;

public record DatosDisponibilidadRequest(
        LocalDate fecha,
        Long canchaId
) {
}
