package JMR.API.domain.Consulta;


import JMR.API.domain.Consulta.Validaciones.Validador;
import JMR.API.domain.Medico.Medico;
import JMR.API.domain.Medico.MedicoRepository;
import JMR.API.domain.Paciente.PacienteRepository;
import JMR.API.domain.ValidacionExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<Validador> validadores;

    public void Reservar(DatosReservaConsulta datos) {
        if(!pacienteRepository.existsById(datos.idPaciente()))
        {throw new ValidacionExcepcion("Paciente no encontrado");}

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico()))
        {throw new ValidacionExcepcion("Medico no encontrado");}

        validadores.forEach(v -> v.validar(datos));

        var medico = ElegirMedico(datos);
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
    }

    private Medico ElegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null) {
            throw new ValidacionExcepcion("Campo obligatorio: Elegir especialidad");
        }
        return medicoRepository.ElegirMedicoDisponible(datos.especialidad(), datos.fecha());
    }
}
