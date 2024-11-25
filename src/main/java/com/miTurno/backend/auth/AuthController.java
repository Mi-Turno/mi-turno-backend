package com.miTurno.backend.auth;


import com.miTurno.backend.request.UsuarioLoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UsuarioLoginRequest usuarioLoginRequest){
        return ResponseEntity.ok(new AuthResponse());
    }
    @PostMapping("/register")
    public String register(){
        return "register";
    }

}
