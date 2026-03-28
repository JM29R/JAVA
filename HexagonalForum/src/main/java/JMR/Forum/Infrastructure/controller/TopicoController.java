package JMR.Forum.Infrastructure.controller;


import JMR.Forum.Infrastructure.Dtos.Request.TopicoRequest;
import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.TopicoResponse;
import JMR.Forum.application.service.TopicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @PostMapping
    public ResponseEntity<TopicoResponse> crearTopico(@RequestBody TopicoRequest topicoDTO) {
        TopicoResponse response = topicoService.crearTopico(topicoDTO);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/todos")
    public ResponseEntity<List<TopicoResponse>> obtenerTodosTopicos() {
        List<TopicoResponse> response = topicoService.buscarTodos();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{id}")
    public ResponseEntity<TopicoResponse> obtenerTopico(@PathVariable Long id) {
        TopicoResponse response = topicoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    @Transactional
   public ResponseEntity<Boolean> eliminarTopico(@PathVariable Long id,@RequestBody UsuarioRequest usuarioRequest) {
        topicoService.eliminar(id,usuarioRequest);
        return ResponseEntity.noContent().build();


   }


}
