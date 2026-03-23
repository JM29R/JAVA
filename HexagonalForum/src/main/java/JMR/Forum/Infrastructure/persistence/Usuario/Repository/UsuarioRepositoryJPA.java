package JMR.Forum.Infrastructure.persistence.Usuario.Repository;

import JMR.Forum.Infrastructure.persistence.Usuario.Entity.UsuarioEntity;
import JMR.Forum.Infrastructure.persistence.Usuario.Mapper.UsuarioMapper;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UsuarioRepositoryJPA implements UsuarioRepository {

    private final SpringDataUsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario guardar(Usuario usuario) {

        UsuarioEntity entity = usuarioMapper.toEntity(usuario);

        UsuarioEntity saved = repository.save(entity);

        return usuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {

        return repository.findById(id)
                .map(usuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> listar() {

        return repository.findAll()
                .stream()
                .map(usuarioMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existePorId(Long id) {

        return repository.existsById(id);
    }

    @Override
    public Optional<Usuario> findByNombre(String username) {
        UsuarioEntity user=  repository.findByNombre(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Usuario usuario = usuarioMapper.toDomain(user);
        return Optional.of(usuario);
    }

}
