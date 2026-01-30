package JMR.API.domain.Consulta.Validaciones;

import JMR.API.domain.Consulta.DatosReservaConsulta;
import JMR.API.domain.Medico.MedicoRepository;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoActivo implements Validador  {

    @Autowired
    private MedicoRepository repository;

    public void validar(DatosReservaConsulta datos){
        if(datos.idMedico() == null){
            return;
        }
        var MedicoActivo = repository.findActivoById(datos.idMedico());
        if(MedicoActivo == false){
            throw new ValidacionExcepcion("Medico no encontrado");
        }

    }
}
