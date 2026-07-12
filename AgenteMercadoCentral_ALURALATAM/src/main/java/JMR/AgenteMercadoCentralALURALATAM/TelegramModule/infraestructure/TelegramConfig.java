package JMR.AgenteMercadoCentralALURALATAM.TelegramModule.infraestructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Configuration
public class TelegramConfig {

    @Value("${TL_TOKEN}")
    private String botToken;

    @Bean
    public TelegramClient telegramClient() {
        System.out.println("🤖 Inicializando cliente de Telegram...");
        try {
            // Conexión directa (sin proxy)
            TelegramClient client = new OkHttpTelegramClient(botToken);
            System.out.println("✅ Cliente de Telegram creado exitosamente");
            return client;
        } catch (Exception e) {
            System.err.println("❌ Error al crear cliente de Telegram: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el cliente de Telegram", e);
        }
    }
}