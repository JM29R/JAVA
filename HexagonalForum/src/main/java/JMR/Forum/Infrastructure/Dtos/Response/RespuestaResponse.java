package JMR.Forum.Infrastructure.Dtos.Response;

import java.time.LocalDateTime;

public record RespuestaResponse(
        Long id,
        String contenido,
        LocalDateTime fechaCreacion,
        Long topicoId,
        String autorNombre
) {
}
