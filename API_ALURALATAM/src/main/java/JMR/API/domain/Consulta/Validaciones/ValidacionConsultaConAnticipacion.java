package JMR.API.domain.Consulta.Validaciones;

import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacionConsultaConAnticipacion implements Validador {

    public void validar(DatosReservaConsulta datos){

        var fechaConsulta = datos.fecha();
        var ahora = LocalDateTime.now();
        var DiferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();

        if (DiferenciaEnMinutos < 30) {
            throw new ValidacionExcepcion("Seleccionar horario con mas de 30 minutos de anticipacion");
        }

    }
}
