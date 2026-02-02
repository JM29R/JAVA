package JMR.FOROHUB.Topicos;

import java.time.LocalDateTime;

public record DatosListaTopicos(
       Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        String autor,
        String curso
                                    ) {
    public DatosListaTopicos(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getAutor(),
                topico.getCurso()

        );
    }
}
