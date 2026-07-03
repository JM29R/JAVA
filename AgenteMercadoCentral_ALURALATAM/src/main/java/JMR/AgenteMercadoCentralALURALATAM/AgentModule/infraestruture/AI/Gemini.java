package JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Answer;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Prompt;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.AiModelPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Gemini implements AiModelPort {

    private final ChatClient chatClient;

    public Gemini(@Qualifier("geminiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public Answer generate(Prompt prompt) {

        Answer answer = chatClient.prompt(prompt.value())
                .call()
                .entity(Answer.class);

        System.out.println("Answer desde Gemini : " + answer.toString());

        return answer;
    }
}
