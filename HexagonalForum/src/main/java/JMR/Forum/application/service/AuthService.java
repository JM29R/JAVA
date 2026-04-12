package JMR.Forum.application.service;

import JMR.Forum.Infrastructure.Dtos.Request.UsuarioLoginRequest;
import JMR.Forum.Infrastructure.Dtos.Response.JWTResponse;
import JMR.Forum.Infrastructure.Security.JwtService;
import JMR.Forum.domain.model.Usuario.Usuario;
import JMR.Forum.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public JWTResponse login(UsuarioLoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.nombre(),
                            request.password()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByNombre(request.nombre())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(
                usuario.getNombre(),
                usuario.getRol().name()
        );

        return new JWTResponse(token, usuario.getId());
    }
}
