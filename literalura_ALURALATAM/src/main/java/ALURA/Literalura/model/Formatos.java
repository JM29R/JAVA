package ALURA.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Formatos(
        @JsonAlias("text/html") String html,
        @JsonAlias("application/epub+zip") String epub,
        @JsonAlias("text/plain; charset=utf-8") String textoPlano,
        @JsonAlias("application/rdf+xml") String rdf,
        @JsonAlias("image/jpeg") String jpeg,
        @JsonAlias("application/zip") String zip) {

    // Método para obtener formatos disponibles como String
    public String getFormatosDisponibles() {
        StringBuilder sb = new StringBuilder();

        if (html != null && !html.isEmpty())
            sb.append("HTML, ");
        if (epub != null && !epub.isEmpty())
            sb.append("EPUB, ");
        if (textoPlano != null && !textoPlano.isEmpty())
            sb.append("TXT, ");
        if (zip != null && !zip.isEmpty())
            sb.append("ZIP, ");

        String result = sb.toString();
        return result.isEmpty() ? "No disponible" : result.replaceAll(", $", "");
    }
}