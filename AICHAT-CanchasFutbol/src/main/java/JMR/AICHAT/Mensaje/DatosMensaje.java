package JMR.AICHAT.Mensaje;

import java.time.LocalDateTime;

public record DatosMensaje(
        String telefono,
        String mensajeUsuario,
        String respuestaSistema,
        String intencion,
        LocalDateTime fechayhora

) {
}
