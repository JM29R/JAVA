package JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Prompt;

public interface AiModelPort {
    Answer generate(Prompt prompt);
}
