package JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.service;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Context;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Prompt;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.AiModelPort;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.ContextProviderPort;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.PromptBuilderPort;
import org.springframework.stereotype.Service;

@Service
public class DefaultKnowledgeAgent implements KnowledgeAgent {
    private final ContextProviderPort contextProvider;
    private final PromptBuilderPort promptBuilder;
    private final AiModelPort aiModel;

    public DefaultKnowledgeAgent(
            ContextProviderPort contextProvider,
            PromptBuilderPort promptBuilder,
            AiModelPort aiModel) {

        this.contextProvider = contextProvider;
        this.promptBuilder = promptBuilder;
        this.aiModel = aiModel;
    }

    @Override
    public Answer ask(Question question) {

        Context context =
                contextProvider.retrieve(question);

        if (context.value() != null && context.value().startsWith("Pedido")) {

            return new Answer(context.value());
        }

        Prompt prompt =
                promptBuilder.build(question, context);

        return aiModel.generate(prompt);
    }
}
