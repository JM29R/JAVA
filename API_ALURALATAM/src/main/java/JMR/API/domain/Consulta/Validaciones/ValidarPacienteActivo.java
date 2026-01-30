package JMR.API.domain.Consulta.Validaciones;

import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.Paciente.PacienteRepository;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteActivo implements Validador  {

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos){

        var PacienteActivo = repository.findActivoById(datos.idPaciente());

        if (PacienteActivo != true){
            throw new ValidacionExcepcion("Paciente no encontrado");
        }
    }
}
