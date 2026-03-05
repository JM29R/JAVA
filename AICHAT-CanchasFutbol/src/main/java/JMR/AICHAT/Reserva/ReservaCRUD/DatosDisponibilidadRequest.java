package JMR.AICHAT.Reserva.ReservaCRUD;

import java.time.LocalDate;

public record DatosDisponibilidadRequest(
        LocalDate fecha,
        Long canchaId
) {
}
