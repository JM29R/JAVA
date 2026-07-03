package JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.prompt;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Context;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Prompt;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.PromptBuilderPort;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder implements PromptBuilderPort {

    @Override
    public Prompt build(Question question, Context context) {

        String prompt = """
                Eres un asistente especializado de Mercado Central 24h.
                
                Tu única función es responder la pregunta del cliente utilizando EXCLUSIVAMENTE la información contenida en el contexto proporcionado.
                
                ## Reglas
                
                - Utiliza únicamente la información del contexto.
                - No inventes información ni utilices conocimiento externo.
                - Si la respuesta no se encuentra en el contexto, responde exactamente:
                  "No encontré información en los documentos disponibles para responder tu pregunta."
                - Si la pregunta es ambigua y el contexto no permite responder con precisión, solicita una aclaración de forma breve y amable.
                - No respondas preguntas que no estén relacionadas con Mercado Central 24h.
                - Sé claro, preciso y profesional.
                
                ## Contexto
                
                %s
                
                ## Pregunta
                
                %s
              
                """.formatted(
                context.value(),
                question.value()
        );

        return new Prompt(prompt);

    }
    
}
