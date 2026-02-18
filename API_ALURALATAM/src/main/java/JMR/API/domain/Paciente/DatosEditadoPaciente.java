package JMR.API.domain.Paciente;

import JMR.API.domain.Direccion.DatosDireccion;
import jakarta.validation.constraints.NotNull;

public record DatosEditadoPaciente(
        @NotNull Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
) {
}
