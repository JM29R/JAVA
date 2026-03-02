package JMR.AICHAT.Controller;


import JMR.AICHAT.Cancha.*;
import JMR.AICHAT.Reserva.Reserva;
import JMR.AICHAT.Reserva.ReservaMapper;
import JMR.AICHAT.Reserva.ReservaResponse;
import JMR.AICHAT.Service.CanchaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/API/canchas")

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

    @GetMapping("/todas")
    public ResponseEntity listarTodas() {
        List<Cancha> canchas = canchaRepository.findAll();
        List<CanchaResponse> respuestas = canchaRepository.findAll()
                .stream()
                .map(CanchaMapper::toDTO)
                .toList();
        return ResponseEntity.ok(respuestas);

    }



}
