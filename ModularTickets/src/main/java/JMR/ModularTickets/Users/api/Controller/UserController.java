package JMR.ModularTickets.Users.api.Controller;


import JMR.ModularTickets.Users.api.Dtos.UserRequest;
import JMR.ModularTickets.Users.api.Dtos.UserResponse;
import JMR.ModularTickets.Users.infraestructure.Service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.ok(service.create(userRequest));

    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> all(){
        return  ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable @Min(1) Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> findByUsername(@PathVariable @NotBlank String username){
        return ResponseEntity.ok(service.findByUsername(username));
    }

    @Transactional
    @PutMapping("/unban/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable @Min(1) Long id){

        return ResponseEntity.ok(service.unban(id));

    }

    @Transactional
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Min(1) Long id){
        service.delete(id);
    }







}
