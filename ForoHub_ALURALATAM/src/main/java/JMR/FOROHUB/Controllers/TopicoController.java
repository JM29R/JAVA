package JMR.FOROHUB.Controllers;


import JMR.FOROHUB.Topicos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/Topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity Regsitrar(@RequestBody @Valid DatosRegistroTopicos datos, UriComponentsBuilder uriBuilder) {
        var topico = new Topico(datos);
        topicoRepository.save(topico);
        var uri = uriBuilder.path("/Topicos{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalladoTopico(topico));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<DatosListaTopicos>> ListaTopicos(@PageableDefault(size =  10 , sort = {"titulo"}) Pageable pageable ){

        var page =topicoRepository.findAllByStatusTrue(pageable).map(DatosListaTopicos::new);
        return ResponseEntity.ok(page);
    }


    @Transactional
    @PutMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity Editar(@RequestBody @Valid DatosActualizarTopicos datos){
        var topico=topicoRepository.getReferenceById(datos.id());
        topico.actualizar(datos);
        return ResponseEntity.ok(new DatosDetalladoTopico(topico));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){
        var topico=topicoRepository.getReferenceById(id);
        topico.eliminar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<DatosListaTopicos> buscar(@PathVariable Long id){
        Topico topico=topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosListaTopicos(topico));
    }

}
