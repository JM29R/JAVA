package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.infraestructure;

import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.Pedidos;
import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.PedidosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PedidosRepositoryImplements implements PedidosRepository {

    private final PedidosRepositoryJPA repositoryJPA;

    private final PedidosMapperJPA mapper;

    @Override
    public Pedidos save(Pedidos pedidos) {
        PedidosEntityJPA JPA = mapper.ToEntity(pedidos);

        PedidosEntityJPA entity = repositoryJPA.save(JPA);

        return mapper.ToDomain(entity);

    }

    @Override
    public Pedidos FindById(Long id) {
        PedidosEntityJPA JPA = repositoryJPA.findById(id).orElse(null);

        Pedidos pedido = mapper.ToDomain(JPA);
        return pedido;
    }

    @Override
    public Pedidos FindByIdTelegram(Long idTelegram) {
        PedidosEntityJPA JPA = repositoryJPA.findByIdTelegram(idTelegram).orElse(null);

        Pedidos pedido = mapper.ToDomain(JPA);
        return pedido;

    }

    @Override
    public List<Pedidos> findAll() {

        List <PedidosEntityJPA> JPA = repositoryJPA.findAll();

        return JPA.stream()
                .map(mapper::ToDomain)
                .toList();

    }

    @Override
    public List<Pedidos> findAllById(Long id) {
        List <PedidosEntityJPA> JPA = repositoryJPA.findAllById(id);

    return JPA.stream()
                .map(mapper::ToDomain)
                .toList();
    }


    @Override
    public List<Pedidos> findAllByIdTelegram(Long idTelegram) {
        List <PedidosEntityJPA> JPA = repositoryJPA.findAllByIdTelegram(idTelegram);

        return JPA.stream()
                .map(mapper::ToDomain)
                .toList();
    }


    @Override
    public void deleteById(Long id) {

        repositoryJPA.deleteById(id);

    }

    @Override
    public void deleteByIdTelegram(Long idTelegram) {

        repositoryJPA.deleteByIdTelegram(idTelegram);

    }


}
