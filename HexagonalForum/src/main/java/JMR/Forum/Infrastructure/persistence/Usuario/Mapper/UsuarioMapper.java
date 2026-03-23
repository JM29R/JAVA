package JMR.Forum.Infrastructure.persistence.Usuario.Mapper;



import JMR.Forum.Infrastructure.persistence.Usuario.Entity.UsuarioEntity;
import JMR.Forum.domain.model.Usuario.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity usuarioEntity){
        Usuario usuario = new Usuario();
        usuario.setId(usuarioEntity.getId());
        usuario.setNombre(usuarioEntity.getNombre());
        usuario.setPassword(usuarioEntity.getPassword());
        usuario.setActivo(usuarioEntity.getActivo());
        usuario.setRol(usuarioEntity.getRole());
        return usuario;
    }

    public UsuarioEntity toEntity(Usuario usuario){
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(usuario.getId());
        usuarioEntity.setNombre(usuario.getNombre());
        usuarioEntity.setPassword(usuario.getPassword());
        usuarioEntity.setActivo(usuario.isActivo());
        usuarioEntity.setRole(usuario.getRol());
        return usuarioEntity;
    }





}
