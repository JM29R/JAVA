package JMR.Forum.domain.model;

import JMR.Forum.domain.model.Usuario.Usuario;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Respuesta {
    private Long id;

    private LocalDateTime fechaCreacion;

    private String contenido;

    private Usuario autor;

    private Topico topico;




}
