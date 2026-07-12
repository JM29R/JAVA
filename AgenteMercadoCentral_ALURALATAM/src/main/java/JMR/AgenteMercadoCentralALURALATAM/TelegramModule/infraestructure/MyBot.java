package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure;



import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.AgentService;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MessageRequest;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.api.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class MyBot implements SpringLongPollingBot {

    @Value("${TL_TOKEN}")
    private String botToken;

    private final AgentService llmService;

    private final TelegramClient telegramClient;

    @Autowired
    public MyBot(AgentService llmService, @Value("${TL_TOKEN}") String botToken) {
        this.llmService = llmService;
        this.botToken = botToken;
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Autowired
    private TelegramService service;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {

        return updates -> {
            for (Update update : updates) {
                if (update.hasMessage() && update.getMessage().hasText()) {
                    String text = update.getMessage().getText();
                    Long chatId = update.getMessage().getChatId();

                    MessageRequest request = new MessageRequest(text);

                    String response = llmService.MenssageResponse(request,chatId);

                    service.RegisterChat(chatId);

                    service.RegisterMessage(chatId,text,response);

                     //Responder
                    SendMessage message = SendMessage.builder()
                            .chatId(chatId.toString())
                            .text(response)
                            .build();

                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        System.out.println("ERROR enviando mensaje");
                        System.out.println("   Chat ID: " + chatId);
                        System.out.println("   Mensaje: " + text);
                        System.out.println("   Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };


    }
}

