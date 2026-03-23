package JMR.Forum.Infrastructure.persistence.Topico.Repository;

import JMR.Forum.Infrastructure.persistence.Topico.Entity.TopicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTopicoRepository
        extends JpaRepository<TopicoEntity, Long> {
}