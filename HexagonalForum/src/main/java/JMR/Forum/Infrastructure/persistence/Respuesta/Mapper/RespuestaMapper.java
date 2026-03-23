package JMR.Forum.Infrastructure.persistence.Respuesta.Mapper;


import JMR.Forum.Infrastructure.persistence.Respuesta.Entity.RespuestaEntity;
import JMR.Forum.Infrastructure.persistence.Topico.Entity.TopicoEntity;
import JMR.Forum.Infrastructure.persistence.Usuario.Mapper.UsuarioMapper;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RespuestaMapper {

    private final UsuarioMapper usuarioMapper;

    public Respuesta toDomain(RespuestaEntity entity) {
        if (entity == null) return null;

        return Respuesta.builder()
                .id(entity.getId())
                .contenido(entity.getContenido())
                .fechaCreacion(entity.getFechaCreacion())
                .autor(entity.getAutor() != null
                        ? usuarioMapper.toDomain(entity.getAutor())
                        : null)
                .topico(entity.getTopico() != null
                        ? Topico.builder().id(entity.getTopico().getId()).titulo(entity.getTopico().getTitulo()).build()
                        : null)
                .build();
    }


    public RespuestaEntity toEntity(Respuesta respuesta) {
        if (respuesta == null) return null;

        RespuestaEntity entity = new RespuestaEntity();
        entity.setId(respuesta.getId());
        entity.setContenido(respuesta.getContenido());
        entity.setFechaCreacion(respuesta.getFechaCreacion());

        if (respuesta.getAutor() != null) {
            entity.setAutor(usuarioMapper.toEntity(respuesta.getAutor()));
        }

        if (respuesta.getTopico() != null) {
            TopicoEntity te = new TopicoEntity();
            te.setId(respuesta.getTopico().getId());
            entity.setTopico(te);
        }

        return entity;
    }
}
