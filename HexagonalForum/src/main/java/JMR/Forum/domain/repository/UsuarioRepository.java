package JMR.Forum.domain.repository;

import JMR.Forum.domain.model.Usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    List<Usuario> listar();

    boolean existePorId(Long id);

    Optional<Usuario> findByNombre(String username);

}
