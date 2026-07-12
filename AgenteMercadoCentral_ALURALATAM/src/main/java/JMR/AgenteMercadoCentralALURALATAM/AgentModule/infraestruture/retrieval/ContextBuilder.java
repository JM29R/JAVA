package JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.retrieval;

import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Context;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.model.Question;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.domain.port.ContextProviderPort;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.AI.Groq;
import JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.document.PdfTextExtractor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@AllArgsConstructor
public class ContextBuilder implements ContextProviderPort {

    private final PdfTextExtractor pdfTextExtractor;

    private final Groq groq;

    @Override
    public Context retrieve(Question question) {

        String Detectedintent = groq.Detectedintent(question).name();

        if (Detectedintent != null && Detectedintent.startsWith("Pedido")) {
            return new Context(Detectedintent);
        }

        try {
            String text = pdfTextExtractor.read(Detectedintent +".pdf");
            return new Context(text);

        } catch (IOException e) {
            throw new RuntimeException("Error al leer el PDF", e);
        }
    }
}
