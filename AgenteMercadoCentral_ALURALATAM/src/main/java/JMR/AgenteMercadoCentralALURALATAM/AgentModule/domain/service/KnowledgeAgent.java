package JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.service;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;

public interface KnowledgeAgent {

    Answer ask(Question question);
}
