package JMR.API.infra.Security;



import JMR.API.domain.Usuario.Usuario;
import JMR.API.domain.Usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = RecuperarToken(request);
        if(token!=null) {
            var subject = tokenService.getSubject(token);
            var usuario =usuarioRepository.findByLogin(subject);
            var autenticado = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticado);

        }

        filterChain.doFilter(request, response);

    }

    private String RecuperarToken(HttpServletRequest request) {
        var authToken = request.getHeader("Authorization");
        if (authToken != null) {
            return authToken.replace("Bearer ", "");


        }
        return null;
    }
}
