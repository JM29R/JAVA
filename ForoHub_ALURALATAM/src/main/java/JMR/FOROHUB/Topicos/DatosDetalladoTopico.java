package JMR.FOROHUB.Topicos;

import java.time.LocalDateTime;

public record DatosDetalladoTopico(
        Long id,
         String titulo,
         String mensaje,
         String autor,
         String curso,
         LocalDateTime fecha,
         boolean status
) {
    public DatosDetalladoTopico(Topico topico){
       this(
               topico.getId(),
               topico.getTitulo(),
               topico.getMensaje(),
               topico.getAutor(),
               topico.getCurso(),
               topico.getFecha(),
               topico.isStatus()
       );
    }
}
