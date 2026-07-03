package JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.configuration;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean("groqChatClient")
    public ChatClient groqChatClient(
            @Qualifier("openAiChatModel") ChatModel model
    ) {
        return ChatClient.builder(model).build();
    }

    @Bean("geminiChatClient")
    public ChatClient geminiChatClient(
            @Qualifier("googleGenAiChatModel") ChatModel model
    ) {
        return ChatClient.builder(model).build();
    }
}

