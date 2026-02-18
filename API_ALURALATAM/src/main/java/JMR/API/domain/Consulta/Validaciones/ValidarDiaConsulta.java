package JMR.API.domain.Consulta.Validaciones;

import JMR.API.domain.Consulta.ConsultaRepository;
import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarDiaConsulta implements Validador {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){

        var primerHorario = datos.fecha().withHour(7);
        var UltimoHorario = datos.fecha().withMinute(18);
        var PacienteconConsulta = repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(),primerHorario,UltimoHorario);

        if(PacienteconConsulta == true) {
            throw new ValidacionExcepcion("Paciente no encontrado");
        }
    }
}
