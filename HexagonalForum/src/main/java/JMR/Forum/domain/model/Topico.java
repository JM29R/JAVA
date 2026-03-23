package JMR.Forum.domain.model;


import JMR.Forum.domain.model.Usuario.Usuario;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topico {

    private Long id;
    private String titulo;
    private String mensaje;
    private Usuario autor;




}