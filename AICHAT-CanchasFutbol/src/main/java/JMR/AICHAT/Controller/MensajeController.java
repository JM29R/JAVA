package JMR.AICHAT.Controller;


import JMR.AICHAT.DTOs.DatosMensaje;
import JMR.AICHAT.DTOs.Outputs.MensajeResponse;
import JMR.AICHAT.Mapper.MensajeMapper;
import JMR.AICHAT.Mensaje.Mensaje;
import JMR.AICHAT.Mensaje.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarMensaje(@PathVariable Long id){
        mensajeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/{telefono}")
    public ResponseEntity eliminarMensajePorTelefono(@PathVariable String telefono){
        mensajeRepository.deleteByTelefono(telefono);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/todos")
    public ResponseEntity eliminarTodos(){
        mensajeRepository.deleteAll();
        return ResponseEntity.ok().build();
    }


}
