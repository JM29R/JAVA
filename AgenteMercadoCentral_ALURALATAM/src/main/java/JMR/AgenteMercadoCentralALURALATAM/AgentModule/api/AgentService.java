package JMR.AgenteMercadoCentralALURALATAM.AgentModule.api;


import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MenssageResponse;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MessageRequest;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Context;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Prompt;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI.Gemini;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI.Groq;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI.Intencion;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.prompt.PromptBuilder;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.retrieval.ContextBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AgentService {

    private final Groq groq;

    private final Gemini gemini;

    private final PromptBuilder promptBuilder;

    private final ContextBuilder contextBuilder;

    public MenssageResponse MenssageResponse(MessageRequest request){

        Question question = new Question(request.message());

        Intencion intencion = groq.Detectedintent(question);
        if(intencion == Intencion.Pedido){

            //camino de pedidos

            return null;

        }else{

            Context context = contextBuilder.retrieve(question);

            Prompt prompt=  promptBuilder.build(question, context);

            Answer answer = gemini.generate(prompt);

            System.out.println("Answer desde service: " + answer);

            return new MenssageResponse(answer.answer());

        }



    }




}
