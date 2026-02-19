package JMR.AICHAT.DTOs.Outputs;

import java.time.LocalDateTime;

public record MensajeResponse(
        Long id,
        String telefono,
        String mensajeUsuario,
        String respuestaSistema,
        String intencion,
        LocalDateTime fechayhora

) {
}
