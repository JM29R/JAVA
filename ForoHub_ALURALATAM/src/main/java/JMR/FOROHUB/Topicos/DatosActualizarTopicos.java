package JMR.FOROHUB.Topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopicos(   @NotNull Long id,
                                        @NotBlank String titulo,
                                        @NotBlank String mensaje,
                                        @NotBlank String autor,
                                        @NotBlank String curso) {
}
