package com.JMR.IntegracionAI.Controller;

import com.JMR.IntegracionAI.Tokens.ContadorDeToken;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorizador")
public class CategorizadorController {

    private ChatClient chatClient;
    public CategorizadorController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("gpt-4o-mini")
                        .build())
                .build();
    }

    @GetMapping
    public String Categorizador(String producto) {
        var system ="eres un categorizador de producto";
        var contador = new ContadorDeToken();
        var tokens = contador.Contador(system, producto);

        if (tokens >= 1000){
            return this.chatClient.prompt()
                    .system(system)
                    .user(producto)
                    .options(OpenAiChatOptions.builder()
                            .model("gpt-4o")
                            .temperature(0.7)
                            .build())
                    .advisors(new SimpleLoggerAdvisor())
                    .call()
                    .content();

        }else  {
            return "Sin saldo en Openai para categorizar :(";
        }
    }

}
