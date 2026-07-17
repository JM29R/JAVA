package JMR.AgenteMercadoCentralALURALATAM.AgentModule.api;



import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MessageRequest;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.AiModelPort;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.service.KnowledgeAgent;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI.Groq;
import JMR.AgenteMercadoCentralALURALATAM.PedidosModule.api.PedidosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AgentService {

    private final KnowledgeAgent knowledgeAgent;

    private final Groq groq;

    private final PedidosService pedidosService;

    public String MenssageResponse(MessageRequest request, Long chatId) {

        System.out.println("MenssageRequest " + request.message() );

        Question question = new Question(request.message());

        Answer answer = knowledgeAgent.ask(question);

        if (answer != null && answer.answer().startsWith("Pedido")) {

            String intencion = answer.answer();

            String pedidos = null;

            switch (intencion) {
                case "Pedido_CREAR",
                     "Pedido_AGREGAR",
                     "Pedido_QUITAR" -> {

                    pedidos = groq.ExtractPedido(question);
                }
            }

            return pedidosService.CreatebyTelegram(answer, pedidos, chatId);

        } else {

            return answer.answer();
        }



    }




}
