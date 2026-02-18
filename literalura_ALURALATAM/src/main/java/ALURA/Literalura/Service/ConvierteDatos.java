package ALURA.Literalura.Service;

import ALURA.Literalura.model.DatosRespuestaGutendex;
import ALURA.Literalura.model.DatosLibros;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConvierteDatos {

    @Autowired
    private ConsumoAPI consumoAPI;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Obtener todos los libros de una página
    public List<DatosLibros> obtenerLibrosDePagina(int pagina) {
        String url = "https://gutendex.com/books/?page=" + pagina;
        String json = consumoAPI.ObtenerDatos(url);

        try {
            DatosRespuestaGutendex respuesta = objectMapper.readValue(json, DatosRespuestaGutendex.class);
            return respuesta.libros();
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir datos: " + e.getMessage(), e);
        }
    }

    // Buscar libros por título
    public List<DatosLibros> buscarLibrosPorTitulo(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        String json = consumoAPI.ObtenerDatos(url);

        try {
            DatosRespuestaGutendex respuesta = objectMapper.readValue(json, DatosRespuestaGutendex.class);
            return respuesta.libros();
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar libros: " + e.getMessage(), e);
        }
    }

    // Obtener solo los títulos
    public List<String> obtenerTitulosDePagina(int pagina) {
        List<DatosLibros> libros = obtenerLibrosDePagina(pagina);
        return libros.stream()
                .map(DatosLibros::titulo)
                .toList();
    }

    // Obtener solo los autores
    public List<String> obtenerAutoresDePagina(int pagina) {
        List<DatosLibros> libros = obtenerLibrosDePagina(pagina);
        return libros.stream()
                .map(DatosLibros::getAutoresComoString)
                .distinct()
                .toList();
    }
}