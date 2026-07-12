package JMR.AgenteMercadoCentralALURALATAM.AgentModule.infraestruture.document;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfTextExtractor {

    public String read(String fileName) throws IOException {

        ClassPathResource resource = new ClassPathResource("pdf/" + fileName);

        try (InputStream inputStream = resource.getInputStream();
             PDDocument document = Loader.loadPDF(inputStream.readAllBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);

        }
    }

}
