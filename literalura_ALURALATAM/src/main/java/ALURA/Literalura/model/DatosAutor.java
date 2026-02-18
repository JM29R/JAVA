package ALURA.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer añoNacimiento,
        @JsonAlias("death_year") Integer añoFallecimiento) {

    public String formatearInformacion() {
        if (añoNacimiento != null && añoFallecimiento != null) {
            return String.format("%s (%d - %d)", nombre, añoNacimiento, añoFallecimiento);
        } else if (añoNacimiento != null) {
            return String.format("%s (Nacido en %d)", nombre, añoNacimiento);
        } else {
            return nombre;
        }
    }
}