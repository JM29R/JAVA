package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.UserTelegram;


import JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure.MessageTelegram.MessageTelegramJPA;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_telegram")
public class ChatTelegramJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idChat;


 @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MessageTelegramJPA> messages = new ArrayList<>();


}
