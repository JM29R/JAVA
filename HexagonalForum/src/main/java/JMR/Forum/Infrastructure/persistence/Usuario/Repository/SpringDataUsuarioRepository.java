package JMR.Forum.Infrastructure.persistence.Usuario.Repository;

import JMR.Forum.Infrastructure.persistence.Usuario.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUsuarioRepository
        extends JpaRepository<UsuarioEntity,Long> {
    Optional<UsuarioEntity> findByNombre(String user);
}
