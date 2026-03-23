package JMR.Forum.Infrastructure.controller;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioRequest;
import JMR.Forum.Infrastructure.Dtos.Response.UsuarioResponse;
import JMR.Forum.application.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> RegistrarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return ResponseEntity.ok(usuarioService.crearUsuario(usuarioRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioResponse> editarUsuario(@PathVariable Long id,@RequestBody UsuarioRequest usuarioRequest){
        return ResponseEntity.ok(usuarioService.editarUsuario(id,usuarioRequest));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.BuscarporID(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioResponse>> ListaUsuario(){
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/IsActivo/{id}")
    public boolean isActivo(@PathVariable Long id){
        return usuarioService.ActivoporID(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UsuarioResponse> deleteUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.eliminarUsuario(id));
    }


}
