package JMR.Forum.Infrastructure.Dtos.Request;

public record RespuestaRequest(
        Long respuestaId,
        String contenido,
        Long topicoId,
        Long autorId
) {
}
