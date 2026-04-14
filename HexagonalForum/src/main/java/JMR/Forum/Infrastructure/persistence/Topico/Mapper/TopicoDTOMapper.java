package JMR.Forum.Infrastructure.persistence.Topico.Mapper;

import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public class TopicoDTOMapper {

    public Topico toDomain(TopicoRequest dto) {

        return Topico.builder()
                .titulo(dto.titulo())
                .mensaje(dto.mensaje())
                .build();
    }

    public TopicoResponse toResponse(Topico topico) {

        if (topico == null) {
            return null;
        }

        return new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor() != null ? topico.getAutor().getId() : null
        );
    }
}