package JMR.AICHAT.Controller;


import JMR.AICHAT.ChatAI.DatosFinalAI;
import JMR.AICHAT.Mensaje.MensajeRequest;
import JMR.AICHAT.Mensaje.MensajeMapper;
import JMR.AICHAT.Mensaje.Mensaje;
import JMR.AICHAT.Mensaje.MensajeRepository;
import JMR.AICHAT.Service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class AIController {


    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private GroqService groqService;

    @PostMapping
    public ResponseEntity<String> reservarConAI(@RequestBody MensajeRequest request) {
        List<Mensaje> historial = mensajeRepository.findTop10ByTelefonoOrderByFechaHoraDesc(request.telefono());
        DatosFinalAI datos = groqService.AnalizarMensaje(request, historial);
        switch (datos.intencion()) {
            case "CREAR":
                String respuesta = groqService.CrearReserva(datos);
                var datosmsj = MensajeMapper.toDatosMensaje(datos, request.mensaje(),respuesta);
                var mensaje =  MensajeMapper.toEntityByDatosMensaje(datosmsj);
                mensajeRepository.save(mensaje);
                return ResponseEntity.ok(respuesta);
            case "CANCELAR":
                String respuesta2 = groqService.CancelarReserva(request);
                var datosmsj2 = MensajeMapper.toDatosMensaje(datos, request.mensaje(),respuesta2);
                var mensaje2 =  MensajeMapper.toEntityByDatosMensaje(datosmsj2);
                mensajeRepository.save(mensaje2);
                return ResponseEntity.ok(respuesta2);
            case "MODIFICAR":
                String respuesta3 = groqService.ModificarReserva(request);
            var datosmsj3 = MensajeMapper.toDatosMensaje(datos, request.mensaje(),respuesta3);
            var mensaje3 =  MensajeMapper.toEntityByDatosMensaje(datosmsj3);
            mensajeRepository.save(mensaje3);
                return ResponseEntity.ok(respuesta3);
            case "CONSULTAR":
                String respuesta4 = groqService.ConsultarReserva(datos);
                var datosmsj4 = MensajeMapper.toDatosMensaje(datos, request.mensaje(),respuesta4);
                var mensaje4 =  MensajeMapper.toEntityByDatosMensaje(datosmsj4);
                mensajeRepository.save(mensaje4);
                return ResponseEntity.ok(respuesta4);
            case "DISPONIBILIDAD":
                String respuesta5 = groqService.HorariosLibres(datos);
                var datosmsj5 = MensajeMapper.toDatosMensaje(datos, request.mensaje(),respuesta5);
                var mensaje5 =  MensajeMapper.toEntityByDatosMensaje(datosmsj5);
                mensajeRepository.save(mensaje5);
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
