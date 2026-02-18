package JMR.AICHAT.DTOs.Inputs;

import java.time.LocalDate;

public record DatosDisponibilidadRequest(
        LocalDate fecha,
        Long canchaId
) {
}
