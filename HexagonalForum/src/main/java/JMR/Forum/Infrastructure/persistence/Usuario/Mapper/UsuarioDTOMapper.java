package JMR.Forum.Infrastructure.persistence.Usuario.Mapper;

import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.Infrastructure.persistence.Usuario.Entity.UsuarioEntity;
import JMR.Forum.domain.model.Usuario.Roles;
import JMR.Forum.domain.model.Usuario.Usuario;
import org.springframework.stereotype.Component;


@Component
public class UsuarioDTOMapper {

    public UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getRol().name(),
                entity.isActivo()
        );
    }

    public Usuario toRequest(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setPassword(request.password());
        usuario.setRol(Roles.ROLE_USER);
        return usuario;
    }

}
