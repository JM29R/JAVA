package JMR.AICHAT.Controller;


import JMR.AICHAT.Mensaje.MensajeResponse;
import JMR.AICHAT.Mensaje.MensajeMapper;
import JMR.AICHAT.Mensaje.Mensaje;
import JMR.AICHAT.Mensaje.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private MensajeRepository mensajeRepository;

    @GetMapping
    public ResponseEntity<List<MensajeResponse>> obtenerTodos() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        List<MensajeResponse> respuesta= mensajes.stream()
                .map(MensajeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{telefono}")
    public ResponseEntity<List<MensajeResponse>> ObtenerTodosPorTelefono(@PathVariable String telefono){
        List<Mensaje> mensajes = mensajeRepository.findByTelefonoOrderByFechaHoraAsc(telefono);
        List<MensajeResponse> respuesta= mensajes.stream()
                .map(MensajeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/intencion/{intencion}")
    public ResponseEntity<List<MensajeResponse>>  ObtenerTodosPorIntencion(@PathVariable String intencion){
        List<Mensaje> mensajes = mensajeRepository.findByIntencion(intencion);
        List<MensajeResponse> respuesta= mensajes.stream()
                .map(MensajeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @Transactional
    @DeleteMapping("/id/{id}")
    public ResponseEntity eliminarMensaje(@PathVariable Long id){
        if (!mensajeRepository.existsById(id)) {
            throw new RuntimeException("Mensaje no encontrado");
        }
        mensajeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/telefono/{telefono}")
    public ResponseEntity eliminarMensajePorTelefono(@PathVariable String telefono){
        mensajeRepository.deleteByTelefono(telefono);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/todos")
    public ResponseEntity eliminarTodos(){
        mensajeRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }


}
