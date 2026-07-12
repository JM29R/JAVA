package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepositoryJPA extends JpaRepository<ChatTelegramJPA, Long> {


    Optional<ChatTelegramJPA> findByidChat(Long idChat);

    Optional<ChatTelegramJPA> findById(Long telegramUserId);

    void deleteByidChat(Long idChat);

    void deleteById(Long userId);
}
