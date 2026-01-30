package JMR.API.domain.Medico;


import JMR.API.domain.Direccion.Direccion;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Table(name = "Medicos")
@Entity(name = "Medicos")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String documento;
    private String telefono;
    private String email;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion datosDireccion;

    public Medico(DatosRegistroMedico datos) {
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.documento = datos.documento();
        this.email = datos.email();
        this.especialidad = datos.especialidad();
        this.datosDireccion = new Direccion(datos.direccion());
        this.telefono = datos.telefono();
    }


    public void actualizar(@Valid DatosEditadoMedicos datos) {
        if(datos.nombre() != null ){this.nombre = datos.nombre();}
        if(datos.telefono() != null ){this.telefono = datos.telefono();}
        if(datos.direccion() != null ){this.datosDireccion.actualizarDireccion(datos.direccion());}
    }

    public void eliminar() {
        this.activo = false;
    }
}
