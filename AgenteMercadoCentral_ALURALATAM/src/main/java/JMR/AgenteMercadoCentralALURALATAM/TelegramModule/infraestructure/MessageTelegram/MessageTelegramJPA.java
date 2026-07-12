package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.MessageTelegram;

import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram.ChatTelegramJPA;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "message_telegram")
@AllArgsConstructor
@NoArgsConstructor
public class MessageTelegramJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private ChatTelegramJPA chat;

    private String messageUser;

    private String messageResponse;

    private LocalDateTime messageTime;



}
