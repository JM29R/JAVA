package JMR.AICHAT.CONTROLLER;



import JMR.AICHAT.CANCHAS.CanchaRepository;
import JMR.AICHAT.RESERVAS.*;
import JMR.AICHAT.SERVICE.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservar")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Transactional
    @PostMapping
    public ResponseEntity Reservar(@RequestBody DatosReserva datos){
        try {
            Reserva reserva = reservaService.reservarCancha(datos);
            return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/modificar")
    public ResponseEntity modificar(@RequestBody @Valid DatosModificarReserva datos) {

        try{
            Reserva reserva = reservaService.modificarReserva(datos);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @Transactional
    @DeleteMapping
    public ResponseEntity Eliminar(@RequestBody @Valid DatosIdentificarReserva datos) {
        try {
            reservaService.EliminarReserva(datos);
            return ResponseEntity.noContent().build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/disponibilidad")
    public ResponseEntity disponibilidad(@RequestBody @Valid DatosDisponibilidad datos) {
        try {
            List<LocalTime> horarios = reservaService.obtenerHorariosDisponibles(datos);
            return ResponseEntity.ok(horarios);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}


