package JMR.API.domain.Paciente;

import JMR.API.domain.Direccion.Direccion;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pacientes")
@Entity(name = "paciente")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String documento;
    private String telefono;
    private String email;

    @Embedded
    private Direccion datosDireccion;

    public Paciente(DatosRegistroPaciente datos){
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.documento = datos.documento();
        this.email = datos.email();
        this.datosDireccion = new Direccion(datos.direccion());
        this.telefono = datos.telefono();
    }

    public void actualizarpaciente(@Valid DatosEditadoPaciente datos) {
        if(datos.nombre() != null ){this.nombre = datos.nombre();}
        if(datos.telefono() != null ){this.telefono = datos.telefono();}
        if(datos.direccion() != null ){this.datosDireccion.actualizarDireccion(datos.direccion());}
    }

    public void eliminarpaciente() {
        this.activo = false;
    }

}
