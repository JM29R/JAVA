package JMR.ModularTickets.auth.infraestructure.Service;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${JWT}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    public String generateToken(String username, String role){

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("authorities", Collections.singletonList("ROLE_" + role));


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();

    }



}
