package JMR.AgenteMercadoCentralALURALATAM.PedidosModule.api;


import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.Estado;
import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.Pedidos;
import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.domain.PedidosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@AllArgsConstructor
@Service
public class PedidosService {

    private final PedidosRepository pedidosRepository;


    public List<Pedidos> findAll() {

        return pedidosRepository.findAll();

    }

    public String CreatebyTelegram(Answer answer, String pedidos, Long IdTelegram) {

        String respuesta = answer.answer();

        switch (respuesta) {

            case "Pedido_CONSULTAS" -> {
                Pedidos p1 = pedidosRepository.FindByIdTelegram(IdTelegram);


                return p1.getPedido();
            }

            case "Pedido_CREAR" -> {
                Pedidos p2 = new Pedidos();
                p2.setIdTelegram(IdTelegram);
                p2.setPedido(pedidos);
                p2.setEstado(Estado.Pendiente);
                pedidosRepository.save(p2);
                return "Pedido creado correctamente";
            }

            case "Pedido_AGREGAR" -> {
                Pedidos p3 = pedidosRepository.FindByIdTelegram(IdTelegram);
                String l1 = p3.getPedido();
                String l2 = l1 + "," + pedidos;
                p3.setPedido(l2);
                pedidosRepository.save(p3);

                return "Se agrego a su pedido " + pedidos;

            }

            case "Pedido_QUITAR" -> {

                Pedidos p4 = pedidosRepository.FindByIdTelegram(IdTelegram);

                String pedido = p4.getPedido();

                List<String> productos = new ArrayList<>(Arrays.asList(pedido.split(",")));

                productos.removeIf(p -> p.trim().equalsIgnoreCase(pedidos.trim()));

                String nuevoPedido = String.join(",", productos);

                p4.setPedido(nuevoPedido);

                pedidosRepository.save(p4);

                return  "Producto quitado correctamente "+nuevoPedido;
            }

            case "Pedido_ELIMINAR" -> {
                pedidosRepository.deleteByIdTelegram(IdTelegram);

                return "Pedido ELIMINADO";
            }

            default -> {
                throw new IllegalArgumentException("Intención desconocida");
            }
        }
    }
}













