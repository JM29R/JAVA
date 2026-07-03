package JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Context;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;

public interface ContextProviderPort {

    Context retrieve(Question question);

}
