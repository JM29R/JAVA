package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageTelegram {

    Long id;
    Long idChat;
    String messageUser;
    String messageResponse;
    LocalDateTime messageTime;

}
