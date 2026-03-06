package JMR.AICHAT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.flyway.enabled=false",
                "api.security.token.secret=test-secret"
        }
)
class AichatApplicationTests {

    @Test
    void contextLoads() {
    }

}