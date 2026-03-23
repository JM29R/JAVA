package JMR.Forum.Infrastructure.persistence.Respuesta.Repository;

import JMR.Forum.Infrastructure.persistence.Respuesta.Entity.RespuestaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataRespuestaRepository extends JpaRepository<RespuestaEntity, Long> {

    List<RespuestaEntity> findListByAutor_Id(Long idAutor);

    RespuestaEntity findByAutor_Id(Long idAutor);

    List<RespuestaEntity> findListByTopico_Id(Long idAutor);
}
