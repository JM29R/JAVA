package JMR.AICHAT.InteligenciaArtificial;

public record MensajeRequest(
        String Intencion,
        String mensaje,
        String nombre,
        String telefono
) {
}
