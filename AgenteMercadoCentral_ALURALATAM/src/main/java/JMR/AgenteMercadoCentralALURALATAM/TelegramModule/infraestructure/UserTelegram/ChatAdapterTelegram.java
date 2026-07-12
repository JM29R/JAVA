package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram;

import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model.ChatTelegram;
import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.port.ChatTelegramRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ChatAdapterTelegram implements ChatTelegramRepository {

    private final ChatRepositoryJPA RepoJPA;

    private final ChatTelegramMapper mapper;

    @Override
    public ChatTelegram create(ChatTelegram chatTelegram) {

        ChatTelegramJPA jpa = mapper.ToEntity(chatTelegram);
        ChatTelegramJPA saved = RepoJPA.save(jpa);

        return mapper.Todomain(saved);
    }

    @Override
    public ChatTelegram FindbyidChat(long idChat) {
        return RepoJPA.findByidChat(idChat)
                .map(mapper::Todomain)
                .orElse(null);
    }

    @Override
    public ChatTelegram FindbyidUser(long idUser) {
        return RepoJPA.findById(idUser)
                .map(mapper::Todomain)
                .orElse(null);
    }

    @Override
    public void Delete(ChatTelegram chatTelegram) {
        ChatTelegramJPA jpa = mapper.ToEntity(chatTelegram);

        RepoJPA.delete(jpa);

    }

    @Override
    public void Deletebyid(long idChat) {

        RepoJPA.deleteById(idChat);


    }

    public void deleteBychat_IdChat(Long telegramChatId){

        RepoJPA.deleteByidChat(telegramChatId);

    }


}
