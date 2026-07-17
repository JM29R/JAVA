package JMR.AgenteMercadoCentralALURALATAM;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.AgentService;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MenssageResponse;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MessageRequest;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI.Groq;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@SpringBootApplication
public class AgenteMercadoCentralAluralatamApplication {
    private final AgentService service;
    private final Groq groq;
	public static void main(String[] args) {
        SpringApplication.run(AgenteMercadoCentralAluralatamApplication.class, args);



	}

    @Bean
    CommandLineRunner test(AgentService service) {
        return args -> {

            Question q = new Question("Hola que metodos de pago manejan?");

            groq.Detectedintent(q);


        };
    }

}
