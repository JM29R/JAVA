package JMR.AICHAT.Controller;


import JMR.AICHAT.DTOs.DatosFinalAI;
import JMR.AICHAT.DTOs.Inputs.MensajeRequest;
import JMR.AICHAT.Service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class AIController {



    @Autowired
    private GroqService groqService;

    @PostMapping
    public ResponseEntity<String> reservarConAI(@RequestBody MensajeRequest request) {
        DatosFinalAI datos = groqService.AnalizarMensaje(request);
        System.out.println("Intecion desde AIController: "+ datos.intencion());
        System.out.println("fecha: " + datos.fecha());

        switch (datos.intencion()) {
            case "CREAR":
                String respuesta = groqService.CrearReserva(datos);
                return ResponseEntity.ok(respuesta);
            case "CANCELAR":
                String respuesta2 = groqService.CancelarReserva(request);
                return ResponseEntity.ok(respuesta2);
            case "MODIFICAR":
                String respuesta3 = groqService.ModificarReserva(request);
                return ResponseEntity.ok(respuesta3);
            case "CONSULTAR":
                String respuesta4 = groqService.ConsultarReserva(datos);
                return ResponseEntity.ok(respuesta4);
            case "DISPONIBILIDAD":
                String respuesta5 = groqService.HorariosLibres(datos);
                return ResponseEntity.ok(respuesta5);
            default:
                String respuesta6 = """
                        No se capto la consulta, podrias ser mas especifico?
                        Utiliza:
                        Cancha:
                        Hora:
                        Fecha:
                        Intencion:(reservar, cancelar, consultar, modificar)
                        """;
                return ResponseEntity.ok(respuesta6);


        }


    }






}
