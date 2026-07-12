package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram;


import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.ChatTelegram;
import org.springframework.stereotype.Component;

@Component
public class ChatTelegramMapper {

    public ChatTelegram Todomain(ChatTelegramJPA chatTelegramJPA) {
        Long id = chatTelegramJPA.getId();
        Long idChat = chatTelegramJPA.getIdChat();
        ChatTelegram domain = new ChatTelegram();
        domain.setId(id);
        domain.setIdChat(idChat);

        return domain;

    }

    public ChatTelegramJPA ToEntity (ChatTelegram domain){
        ChatTelegramJPA jpa = new ChatTelegramJPA();
        jpa.setId(domain.getId());
        jpa.setIdChat(domain.getIdChat());
        return jpa;

    }

}
