package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port;

import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.ChatTelegram;

public interface ChatTelegramRepository {

    ChatTelegram create(ChatTelegram chatTelegram);

    ChatTelegram FindbyidChat(long idChat);

    ChatTelegram FindbyidUser(long idUser);

    void Delete(ChatTelegram chatTelegram);

    void Deletebyid(long idChat);

    void deleteBychat_IdChat(Long telegramChatId);

}
