package ALURA.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuestaGutendex(
        @JsonAlias("count") Integer total,
        @JsonAlias("next") String siguientePagina,
        @JsonAlias("previous") String paginaAnterior,
        @JsonAlias("results") List<DatosLibros> libros) {
}