package JMR.API.controller;

import JMR.API.domain.Medico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class medicoController {
    @Autowired
    private MedicoRepository repository;

    @Transactional
    @PostMapping
    public void Registrar(@RequestBody @Valid DatosRegistroMedico datos){
        repository.save(new Medico(datos));
    }
    @GetMapping
    public Page<DatosListaMedico> Listar_Medicos(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion){
        return repository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
    }
    @Transactional
    @PutMapping
    public void Editar(@RequestBody @Valid DatosEditadoMedicos datos){
        var medico= repository.getReferenceById(datos.id());
        medico.actualizar(datos);

    }

    @Transactional
    @DeleteMapping("/{id}")
    public void Eliminar(@PathVariable Long id){
       var medico = repository.getReferenceById(id);
       medico.eliminar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListaMedico> Buscar_Medicos(@PathVariable Long id){
        Medico medico =repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosListaMedico(medico));
    }

}
