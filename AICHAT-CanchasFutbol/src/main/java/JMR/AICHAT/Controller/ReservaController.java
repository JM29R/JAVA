package JMR.AICHAT.Controller;



import JMR.AICHAT.Cancha.Cancha;
import JMR.AICHAT.Cancha.CanchaRepository;
import JMR.AICHAT.DTOs.Inputs.DatosDisponibilidadRequest;
import JMR.AICHAT.DTOs.Inputs.DatosIdentificarReservaRequest;
import JMR.AICHAT.DTOs.Inputs.DatosModificarReservaRequest;
import JMR.AICHAT.DTOs.Inputs.DatosReservaRequest;
import JMR.AICHAT.Mapper.ReservaMapper;
import JMR.AICHAT.Reserva.*;
import JMR.AICHAT.Service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CanchaRepository canchaRepository;

    @Transactional
    @PostMapping
    public ResponseEntity Reservar(@RequestBody DatosReservaRequest datos){

        Cancha cancha = canchaRepository.findById(datos.canchaId())
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada"));
        Reserva reserva = ReservaMapper.toEntity(datos,cancha);
        return  ResponseEntity.ok(ReservaMapper.toResponse(reserva));

    }

    @Transactional
    @PutMapping("/modificar")
    public ResponseEntity modificar(@RequestBody @Valid DatosModificarReservaRequest datos) {

        try{
            Reserva reserva = reservaService.modificarReserva(datos);
            return ResponseEntity.ok(ReservaMapper.toResponse(reserva));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @Transactional
    @DeleteMapping
    public ResponseEntity Eliminar(@RequestBody @Valid DatosIdentificarReservaRequest datos) {
        try {
            reservaService.EliminarReserva(datos);
            return ResponseEntity.noContent().build(); // 204
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/disponibilidad")
    public ResponseEntity disponibilidad(@RequestBody @Valid DatosDisponibilidadRequest datos) {
        try {
            List<LocalTime> horarios = reservaService.obtenerHorariosDisponibles(datos);
            return ResponseEntity.ok(horarios);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}


