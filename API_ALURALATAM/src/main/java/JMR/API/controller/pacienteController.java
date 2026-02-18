package JMR.API.controller;





import JMR.API.domain.Paciente.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class pacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public void RegistrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos){
        pacienteRepository.save(new Paciente(datos));
    }

    @GetMapping
    public Page<DatosListaPaciente> Listar_Paciente(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion){
        return pacienteRepository.findAllByActivoTrue(paginacion).map(DatosListaPaciente::new);
    }

    @Transactional
    @PutMapping
    public void EditarPaciente(@RequestBody @Valid DatosEditadoPaciente datos){
        var paciente= pacienteRepository.getReferenceById(datos.id());
        paciente.actualizarpaciente(datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void Eliminar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.eliminarpaciente();
    }
}
