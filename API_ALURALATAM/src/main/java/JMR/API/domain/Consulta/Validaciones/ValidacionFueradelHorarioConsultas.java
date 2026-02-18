package JMR.API.domain.Consulta.Validaciones;
import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ValidacionFueradelHorarioConsultas implements Validador  {

    public void validar(DatosReservaConsulta datos){

        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntes= fechaConsulta.getHour() > 7;
        var horarioDespues = fechaConsulta.getHour() < 18;

        if(   horarioAntes || domingo || horarioDespues){
            throw new ValidacionExcepcion("Horario erroneo para validar su consulta");
        }
    }
}
