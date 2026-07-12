package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public interface PedidosRepositoryJPA extends JpaRepository<PedidosEntityJPA, Long> {

    Optional <PedidosEntityJPA> findById(Long id);

    Optional <PedidosEntityJPA> findByIdTelegram(Long idTelegram);

    List<PedidosEntityJPA> findAll();

    List<PedidosEntityJPA> findAllById(Long idUsuario);

    List<PedidosEntityJPA> findAllByIdTelegram(Long idTelegram);

    void deleteById(Long id);

    void deleteByIdTelegram(Long idTelegram);

}
