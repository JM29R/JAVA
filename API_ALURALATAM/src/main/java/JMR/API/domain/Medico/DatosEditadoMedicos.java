package JMR.API.domain.Medico;

import JMR.API.domain.Direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosEditadoMedicos(@NotNull Long id, String nombre, String telefono, DatosDireccion direccion) {



}
