package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.MessageTelegram;


import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.MessageTelegram;

import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram.ChatTelegramJPA;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageTelegramMapper {

    public MessageTelegram ToDomain(MessageTelegramJPA messageTelegram) {

        Long id = messageTelegram.getId();
        Long idChat = messageTelegram.getChat().getId();
        String messageUser = messageTelegram.getMessageUser();
        String messageResponse = messageTelegram.getMessageResponse();
        LocalDateTime messageTime = messageTelegram.getMessageTime();

       MessageTelegram chat = new MessageTelegram();
       chat.setId(id);
       chat.setIdChat(idChat);
       chat.setMessageUser(messageUser);
       chat.setMessageResponse(messageResponse);
       chat.setMessageTime(messageTime);

       return chat;

    }

    public MessageTelegramJPA ToJPA(MessageTelegram messageTelegram, ChatTelegramJPA chatTelegramJPA) {

        Long id = messageTelegram.getId();
        String messageUser = messageTelegram.getMessageUser();
        String messageResponse = messageTelegram.getMessageResponse();
        LocalDateTime messageTime = messageTelegram.getMessageTime();

        MessageTelegramJPA chat = new MessageTelegramJPA();
        chat.setId(id);
        chat.setChat(chatTelegramJPA);
        chat.setMessageUser(messageUser);
        chat.setMessageResponse(messageResponse);
        chat.setMessageTime(messageTime);
        return chat;

    }

}
