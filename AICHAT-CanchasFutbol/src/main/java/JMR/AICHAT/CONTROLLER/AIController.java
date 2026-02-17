package JMR.AICHAT.CONTROLLER;


import JMR.AICHAT.InteligenciaArtificial.MensajeRequest;
import JMR.AICHAT.SERVICE.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {



    @Autowired
    private GroqService groqService;

    @PostMapping("/chat")
    public ResponseEntity<String> reservarConAI(@RequestBody MensajeRequest request) {

        String Intencion = groqService.InterpretarIntencion(request);
        switch (Intencion) {
            case "CREAR":
                String respuesta = groqService.CrearReserva(request);
                return ResponseEntity.ok(respuesta);
            case "CANCELAR":
                String respuesta2 = groqService.CancelarReserva(request);
                return ResponseEntity.ok(respuesta2);
            case "MODIFICAR":
                String respuesta3 = groqService.ModificarReserva(request);
                return ResponseEntity.ok(respuesta3);
            case "CONSULTAR":
                String respuesta4 = groqService.ConsultarReserva(request);
                return ResponseEntity.ok(respuesta4);
            default:
                return ResponseEntity.badRequest().build();
        }

    }






}
