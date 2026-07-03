package JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Context;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Prompt;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;

public interface PromptBuilderPort {

    Prompt build(Question question,
                 Context context);

}
