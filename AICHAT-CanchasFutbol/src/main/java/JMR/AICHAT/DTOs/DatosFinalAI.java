package JMR.AICHAT.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

public record DatosFinalAI(
        String intencion,
        LocalDate fecha,
        LocalTime hora,
        Integer canchaId,
        String nombre,
        String telefono

) {
}
