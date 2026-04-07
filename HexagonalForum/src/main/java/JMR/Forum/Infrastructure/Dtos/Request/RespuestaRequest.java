package JMR.Forum.Infrastructure.Dtos.Request;

public record RespuestaRequest(
        String contenido,
        Long topicoId,
        Long autorId
) {
}
