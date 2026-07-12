package JMR.AgenteMercadoCentralALURALATAM;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.AgentService;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MenssageResponse;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.api.dto.MessageRequest;
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
	public static void main(String[] args) {
        SpringApplication.run(AgenteMercadoCentralAluralatamApplication.class, args);



	}

    @Bean
    CommandLineRunner test(AgentService service) {
        return args -> {

            MessageRequest message = new MessageRequest("Hola que marca de cafe vendeb y en que presentacion?");

            Long chatid=1L;

            String response = service.MenssageResponse(message,chatid);


        };
    }

}
