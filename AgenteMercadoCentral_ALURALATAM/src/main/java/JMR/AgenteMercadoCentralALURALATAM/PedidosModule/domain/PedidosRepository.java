package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain;

import java.util.List;

public interface PedidosRepository {

    Pedidos save(Pedidos pedidos);

    Pedidos FindById(Long id);

    Pedidos FindByIdTelegram(Long idTelegram);

    List<Pedidos> findAll();

    List<Pedidos> findAllById(Long id);

    List<Pedidos> findAllByIdTelegram(Long idTelegram);

    void deleteById(Long id);

    void deleteByIdTelegram(Long idTelegram);


}
