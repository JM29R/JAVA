package com.JMR.IntegracionAI.Controller;

import com.JMR.IntegracionAI.Tokens.ContadorDeToken;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generador")
public class GeneradorProductosController {

    private ChatClient chatClient;

    public GeneradorProductosController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    public String generation() {
        var pregunta = "genera 5 productos ecologicos";
        var system= "eres un generador de diferentes productos";
        var contador = new ContadorDeToken();
        var token = contador.Contador(system, pregunta);

        if(token <= 1000){
            return this.chatClient.prompt()
                    .system(system)
                    .user(pregunta)
                    .call()
                    .content();
        }else {
            return "Sin saldo en Openai :(";
        }
    }
}

