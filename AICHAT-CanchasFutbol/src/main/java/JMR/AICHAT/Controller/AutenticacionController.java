package JMR.AICHAT.Controller;


import JMR.AICHAT.Service.TokenService;
import JMR.AICHAT.Security.DatosLogin;
import JMR.AICHAT.Security.DatosTokenJWT;
import JMR.AICHAT.Usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity InciarSesion(@Valid @RequestBody DatosLogin datos){
        var tokenaut = new UsernamePasswordAuthenticationToken(datos.user(), datos.pass());
        var aut = manager.authenticate(tokenaut);

        var tokenJWT = tokenService.generateToken((Usuario) aut.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }


}
