package JMR.FOROHUB.Controllers;


import JMR.FOROHUB.Topicos.Topico;
import JMR.FOROHUB.Topicos.TopicoRepository;
import JMR.FOROHUB.Usuarios.Usuario;
import JMR.FOROHUB.Usuarios.UsuarioRepository;
import JMR.FOROHUB.Respuestas.DatosRespuesta;
import JMR.FOROHUB.Respuestas.Respuesta;
import JMR.FOROHUB.Respuestas.RespuestaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity crear(@RequestBody @Valid DatosRespuesta datos,
                                Authentication authentication) {

        Topico topico = topicoRepository.getReferenceById(datos.topicoId());

        String username = authentication.getName();
        Usuario autor = (Usuario) authentication.getPrincipal();


        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(datos.mensaje());
        respuesta.setTopico(topico);
        respuesta.setAutor(autor);

        respuestaRepository.save(respuesta);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/topico/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Respuesta> listarPorTopico(@PathVariable Long id) {
        return respuestaRepository.findByTopicoId(id);
    }

}
