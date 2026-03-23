package JMR.Forum.Infrastructure.Dtos.Request;

import java.util.List;

public record TopicoRequest(
        String titulo,
        String mensaje,
        Long idAutor
) {
}
