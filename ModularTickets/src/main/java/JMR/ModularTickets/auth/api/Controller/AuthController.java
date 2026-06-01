package JMR.ModularTickets.auth.api.Controller;

import JMR.ModularTickets.auth.domain.Service.AuthService;
import JMR.ModularTickets.auth.api.Dtos.AuthRequest;
import JMR.ModularTickets.auth.api.Dtos.AuthResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request){

        return ResponseEntity.ok(authService.login(request));

    }
}
