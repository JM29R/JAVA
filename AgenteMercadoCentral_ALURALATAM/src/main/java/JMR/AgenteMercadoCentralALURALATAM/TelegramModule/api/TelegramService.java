package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.api;


import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.ChatTelegram;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.MessageTelegram;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port.ChatTelegramRepository;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port.MessageTelegramRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TelegramService {

    private final ChatTelegramRepository chatTelegramRepository;

    private final MessageTelegramRepository messageTelegramRepository;

    public void RegisterChat(Long idTelegram){
        ChatTelegram chat = chatTelegramRepository.FindbyidChat(idTelegram);

        if (chat == null) {
            chat = new ChatTelegram();
            chat.setIdChat(idTelegram);

            chat = chatTelegramRepository.create(chat);
        }

    }

    public void RegisterMessage(Long idChat, String message, String response){
        MessageTelegram messageTelegram = new MessageTelegram();
        messageTelegram.setIdChat(idChat);
        messageTelegram.setMessageUser(message);
        messageTelegram.setMessageResponse(response);
        messageTelegram.setMessageTime(LocalDateTime.now());

        messageTelegramRepository.create(messageTelegram);

    }


}
