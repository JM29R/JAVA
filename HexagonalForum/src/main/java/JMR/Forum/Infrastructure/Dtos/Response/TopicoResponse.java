package JMR.Forum.Infrastructure.Dtos.Response;

public record TopicoResponse(
        Long id,
        String titulo,
        String mensaje,
        Long idAutor

) {
}
