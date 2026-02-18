package JMR.API.controller;


import JMR.API.domain.Usuario.DatosAutenticacion;
import JMR.API.domain.Usuario.Usuario;
import JMR.API.infra.Security.DatosTokenJWT;
import JMR.API.infra.Security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public ResponseEntity iniciarSesion(@RequestBody @Valid DatosAutenticacion datos){
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(),datos.password());
        var autenticacion = manager.authenticate(authenticationToken);


        var tokenJWT=tokenService.generarToken((Usuario) autenticacion.getPrincipal());


        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

}

