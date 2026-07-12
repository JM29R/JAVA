package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.infraestructure;


import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.Pedidos;
import org.springframework.stereotype.Component;

@Component
public class PedidosMapperJPA {

    public Pedidos ToDomain(PedidosEntityJPA JPA){

        return new Pedidos(
                JPA.getId(),
                JPA.getIdTelegram(),
                JPA.getPedido(),
                JPA.getEstado()
        );
    }

    public PedidosEntityJPA ToEntity(Pedidos Domain){

        return new PedidosEntityJPA(
                Domain.getId(),
                Domain.getIdTelegram(),
                Domain.getPedido(),
                Domain.getEstado()
        );
    }

}
