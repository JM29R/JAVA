package JMR.FOROHUB.Controllers;


import JMR.FOROHUB.Usuarios.DatosAutenticacion;
import JMR.FOROHUB.Usuarios.Usuario;
import JMR.FOROHUB.infra.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity Login(@RequestBody @Valid DatosAutenticacion datos) {

        var token = new UsernamePasswordAuthenticationToken(datos.login(), datos.password());

        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok(tokenService.getToken((Usuario) authentication.getPrincipal()));

    }
}
