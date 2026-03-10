package JMR.AICHAT.Security;


import JMR.AICHAT.Usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class AutenticacionController {

    private final TokenService tokenService;

    private final AuthenticationManager manager;

    @PostMapping("/login")
    public ResponseEntity InciarSesion(@Valid @RequestBody DatosLogin datos){
        var tokenaut = new UsernamePasswordAuthenticationToken(datos.user(), datos.pass());
        var aut = manager.authenticate(tokenaut);

        var tokenJWT = tokenService.generateToken((Usuario) aut.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }


}
