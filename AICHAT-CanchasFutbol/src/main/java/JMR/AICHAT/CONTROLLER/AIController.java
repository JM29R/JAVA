package JMR.AICHAT.CONTROLLER;


import JMR.AICHAT.InteligenciaArtificial.MensajeRequest;
import JMR.AICHAT.RESERVAS.DatosReserva;
import JMR.AICHAT.InteligenciaArtificial.DatosReservaAI;
import JMR.AICHAT.RESERVAS.Reserva;
import JMR.AICHAT.SERVICE.GroqService;
import JMR.AICHAT.SERVICE.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/ai")
public class AIController {



    @Autowired
    private GroqService groqService;

    @PostMapping("/chat")
    public ResponseEntity<String> reservarConAI(@RequestBody MensajeRequest request) {

        String Intencion = groqService.InterpretarIntencion(request);
        String mensaje = groqService.GestionarReserva(request,Intencion);

        return ResponseEntity.ok(mensaje);
    }






}
