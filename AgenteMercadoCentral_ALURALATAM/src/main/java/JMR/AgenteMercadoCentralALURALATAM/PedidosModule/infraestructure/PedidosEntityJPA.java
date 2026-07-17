package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.infraestructure;

import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.Estado;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class PedidosEntityJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idTelegram;

    private String Pedido;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.Pendiente;
}
