package ALURA.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer descargas,
        @JsonAlias("formats") Formatos formatos) {

    public String getAutoresComoString() {
        if (autores == null || autores.isEmpty()) {
            return "Autor desconocido";
        }
        StringBuilder sb = new StringBuilder();
        for (DatosAutor autor : autores) {
            sb.append(autor.nombre()).append(", ");
        }
        return sb.toString().replaceAll(", $", "");
    }

    public String getIdiomasComoString() {
        if (idiomas == null || idiomas.isEmpty()) {
            return "No disponible";
        }
        return String.join(", ", idiomas);
    }

    public String formatearInformacion() {
        return String.format("""
                ==============================
                TÍTULO: %s
                AUTOR(ES): %s
                IDIOMAS: %s
                DESCARGAS: %d
                ==============================
                """,
                titulo != null ? titulo : "Sin título",
                getAutoresComoString(),
                getIdiomasComoString(),
                descargas != null ? descargas : 0);
    }
}