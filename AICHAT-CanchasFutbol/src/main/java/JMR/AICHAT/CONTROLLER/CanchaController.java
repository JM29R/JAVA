package JMR.AICHAT.CONTROLLER;


import JMR.AICHAT.CANCHAS.Cancha;
import JMR.AICHAT.CANCHAS.CanchaRepository;
import JMR.AICHAT.CANCHAS.DatosCancha;
import JMR.AICHAT.SERVICE.CanchaService;
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
    public ResponseEntity Crear(@Valid @RequestBody DatosCancha datos){
        Cancha cancha = canchaService.crearCancha(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(cancha);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity Actualizar(@PathVariable Long id, @Valid @RequestBody DatosCancha datos){
        Cancha cancha = canchaService.actualizarCancha(id, datos);
        return ResponseEntity.ok(cancha);


    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity EliminarCancha(@PathVariable Long id){
        canchaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }




}
