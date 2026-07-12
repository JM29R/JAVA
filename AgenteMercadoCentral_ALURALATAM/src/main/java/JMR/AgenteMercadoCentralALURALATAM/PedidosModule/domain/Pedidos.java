package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Pedidos {

    private Long id;

    private Long idTelegram;

    private String Pedido;

    private Estado estado;

}
