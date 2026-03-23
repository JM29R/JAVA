package JMR.Forum.Infrastructure.persistence.Topico.Mapper;

import JMR.Forum.Infrastructure.persistence.Respuesta.Entity.RespuestaEntity;
import JMR.Forum.Infrastructure.persistence.Respuesta.Mapper.RespuestaMapper;
import JMR.Forum.Infrastructure.persistence.Topico.Entity.TopicoEntity;
import JMR.Forum.Infrastructure.persistence.Usuario.Entity.UsuarioEntity;
import JMR.Forum.domain.model.Respuesta;
import JMR.Forum.domain.model.Topico;
import JMR.Forum.domain.model.Usuario.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class TopicoMapper {

    private final RespuestaMapper respuestaMapper;

    public Topico toDomain(TopicoEntity entity) {
        if (entity == null) return null;




        return Topico.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .mensaje(entity.getMensaje())
                .autor(entity.getAutor() != null
                        ? Usuario.builder().id(entity.getAutor().getId()).nombre(entity.getAutor().getNombre()).build()
                        : null)
                .build();

    }


    public TopicoEntity toEntity(Topico topico) {
        if (topico == null) return null;

        TopicoEntity entity = new TopicoEntity();
        entity.setId(topico.getId());
        entity.setTitulo(topico.getTitulo());
        entity.setMensaje(topico.getMensaje());

        if (topico.getAutor() != null) {
            UsuarioEntity autor = new UsuarioEntity();
            autor.setId(topico.getAutor().getId());
            entity.setAutor(autor);
        }

        return entity;
    }
}
