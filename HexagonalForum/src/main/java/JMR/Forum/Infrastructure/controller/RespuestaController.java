package JMR.Forum.Infrastructure.controller;


import JMR.Forum.Infrastructure.Dtos.Request.RespuestaRequest;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.RespuestaResponse;
import JMR.Forum.application.service.RespuestaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Transactional
    public ResponseEntity<RespuestaResponse> crearRespuesta(@RequestBody RespuestaRequest request) {
        RespuestaResponse response = respuestaService.crear(request);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/topico/{topicoid}")
    public ResponseEntity<List<RespuestaResponse>> ListaRespuestaporTopico(@PathVariable Long topicoid) {
        List<RespuestaResponse> response = respuestaService.listarPorTopico(topicoid);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usuario/{usuarioid}")
    public ResponseEntity<List<RespuestaResponse>> ListaPorUsuario(@PathVariable Long usuarioid) {
        List<RespuestaResponse> response = respuestaService.listarPorUsuario(usuarioid);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id,@RequestBody UsuarioRequest usuarioRequest) {

        respuestaService.eliminar(id,usuarioRequest);
        return ResponseEntity.noContent().build();
    }



}
