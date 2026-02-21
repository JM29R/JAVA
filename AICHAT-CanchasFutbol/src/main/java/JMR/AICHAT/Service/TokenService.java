package JMR.AICHAT.Service;

import JMR.AICHAT.Usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {


    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {

        try {
            var algorithm = Algorithm.HMAC256(secret);
            return  JWT.create()
                        .withIssuer("JMR")
                        .withSubject(usuario.getUser())
                        .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                        .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al crear el token JWT", exception);
        }

    }

    public String getSubject(String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("JMR")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Error al verificar el token JWT", exception);
        }

    }


}
