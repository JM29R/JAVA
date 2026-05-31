package JMR.ModularTickets.auth.api.Controller;

import JMR.ModularTickets.auth.infraestructure.Service.AuthService;
import JMR.ModularTickets.auth.api.Dtos.AuthRequest;
import JMR.ModularTickets.auth.api.Dtos.AuthResponse;
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){

        return ResponseEntity.ok(authService.login(request));

    }
}
