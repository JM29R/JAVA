package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port;

import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.MessageTelegram;

import java.util.List;

public interface MessageTelegramRepository {

    MessageTelegram create(MessageTelegram messageTelegram);

    List<MessageTelegram> findByChatId(Long idChat);

    void delete(MessageTelegram messageTelegram);

    void deletebyidChat(long idChat);

}
