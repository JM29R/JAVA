package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.MessageTelegram;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepositoryJPA extends JpaRepository<MessageTelegramJPA, Long> {

    List<MessageTelegramJPA> findByChat_IdChat(Long idChat);

    void deleteByChat_IdChat(Long idChat);

}
