package JMR.Forum.Infrastructure.persistence.Respuesta.Mapper;

import JMR.Forum.Infrastructure.Dtos.Request.RespuestaRequest;
import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class RespuestaDTOMapper {


    public Respuesta toDomain(RespuestaRequest request, Usuario usuario, Topico topico) {
        return Respuesta.builder()
                .contenido(request.contenido())
                .autor(usuario)
                .topico(topico)
                .fechaCreacion(LocalDateTime.now())
                .build();
    }


    public RespuestaResponse toResponse(Respuesta respuesta) {

        if (respuesta == null) return null;

        return new RespuestaResponse(
                respuesta.getId(),
                respuesta.getContenido(),
                respuesta.getFechaCreacion(),
                respuesta.getTopico() != null ? respuesta.getTopico().getId() : null,
                respuesta.getAutor() != null ? respuesta.getAutor().getNombre() : null
        );
    }

}
