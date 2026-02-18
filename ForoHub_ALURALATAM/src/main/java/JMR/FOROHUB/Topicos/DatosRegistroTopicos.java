package JMR.FOROHUB.Topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopicos(
                                    @NotBlank String titulo,
                                    @NotBlank String mensaje,
                                    @NotBlank String autor,
                                    @NotBlank String curso
                                   ) {
}
