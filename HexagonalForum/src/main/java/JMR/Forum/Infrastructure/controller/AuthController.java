package JMR.Forum.Infrastructure.controller;


import JMR.Forum.Infrastructure.Dtos.Request.UsuarioLoginRequest;
import JMR.Forum.Infrastructure.Dtos.Response.JWTResponse;
import JMR.Forum.application.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginRequest request) {
        try {
            JWTResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }
}
