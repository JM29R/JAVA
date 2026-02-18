package JMR.FOROHUB.infra;


import JMR.FOROHUB.Usuarios.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public  String getToken(Usuario usuario){

        try {
            var algorithm = Algorithm.HMAC256("ForoHub");
            return JWT.create()
                    .withIssuer("jmr")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(Expiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Token invalido",  exception);
        }

    }

    private Instant Expiracion(){
        return LocalDateTime.now().plusSeconds(3600).toInstant(ZoneOffset.of("-03:00"));
    }
}
