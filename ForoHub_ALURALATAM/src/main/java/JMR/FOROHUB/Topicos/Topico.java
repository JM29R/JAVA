package JMR.FOROHUB.Topicos;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "topico")


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private String autor;
    private String curso;
    private LocalDateTime fecha;
    private boolean status;

    public Topico(DatosRegistroTopicos datos) {
        this.id=null;
        this.titulo=datos.titulo();
        this.mensaje=datos.mensaje();
        this.autor=datos.autor();
        this.curso=datos.curso();
        this.fecha=LocalDateTime.now();
        this.status=true;
    }

    public void actualizar(@Valid DatosActualizarTopicos datos){
        this.titulo=datos.titulo();
        this.mensaje=datos.mensaje();
        this.autor=datos.autor();
        this.curso=datos.curso();
        this.fecha=LocalDateTime.now();
    }

    public void eliminar(){this.status = false;}




}
