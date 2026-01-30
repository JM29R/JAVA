package JMR.API.domain.Consulta.Validaciones;

import JMR.API.domain.Consulta.ConsultaRepository;
import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoOtraConsulta implements Validador  {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){
        var MedicoOtraConsulta = repository.existsByMedicoIdAndFecha(datos.idMedico(),datos.fecha());
        if(MedicoOtraConsulta){
            throw new ValidacionExcepcion("Medico tiene otra consulta");
        }
    }
}
