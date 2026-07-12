package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.MessageTelegram;

import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.MessageTelegram;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port.ChatTelegramRepository;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port.MessageTelegramRepository;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram.ChatTelegramJPA;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram.ChatTelegramMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class MessageTelegramAdapter implements MessageTelegramRepository {

    private final MessageRepositoryJPA RepoJPA;

    private final MessageTelegramMapper Mapper;

    private final ChatTelegramRepository ChatPort;

    private final ChatTelegramMapper ChatMapper;

    @Override
    public MessageTelegram create(MessageTelegram messageTelegram) {

        ChatTelegramJPA chat = ChatMapper.ToEntity(ChatPort.FindbyidChat(messageTelegram.getIdChat()));

        MessageTelegramJPA msj = Mapper.ToJPA(messageTelegram,chat);

        MessageTelegramJPA saved = RepoJPA.save(msj);


        return Mapper.ToDomain(saved);
    }

    @Override
    public List<MessageTelegram> findByChatId(Long idChat) {

        List<MessageTelegramJPA> list = RepoJPA.findByChat_IdChat(idChat);

        return list
                .stream()
                .map(Mapper::ToDomain)
                .toList();
    }



    @Override
    public void delete(MessageTelegram messageTelegram) {

        ChatTelegramJPA chat = ChatMapper.ToEntity(ChatPort.FindbyidChat(messageTelegram.getIdChat()));

        MessageTelegramJPA message = Mapper.ToJPA(messageTelegram,chat);

        RepoJPA.delete(message);

    }

    @Override
    public void deletebyidChat(long idChat) {

        RepoJPA.deleteByChat_IdChat(idChat);

    }
}
