package JMR.AICHAT.Controller;


import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.Cancha.CanchaRepository;
import JMR.AICHAT.Cancha.DatosCanchaRequest;
import JMR.AICHAT.Cancha.CanchaMapper;
import JMR.AICHAT.Service.CanchaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cancha")

public class CanchaController {

    @Autowired
    private CanchaRepository canchaRepository;

    @Autowired
    private CanchaService canchaService;

    @Transactional
    @PostMapping
    public ResponseEntity Crear(@Valid @RequestBody DatosCanchaRequest datos){
        Cancha cancha = canchaService.crearCancha(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(CanchaMapper.toDTO(cancha));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity Actualizar(@PathVariable Long id, @Valid @RequestBody DatosCanchaRequest datos){
        Cancha cancha = canchaService.actualizarCancha(id, datos);
        return ResponseEntity.ok(CanchaMapper.toDTO(cancha));


    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity EliminarCancha(@PathVariable Long id){
        canchaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
