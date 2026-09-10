package com.fasttrade.api.auth;

import com.fasttrade.api.config.JwtService;
import com.fasttrade.api.user.User;
import com.fasttrade.api.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public record RegisterRequest(@NotBlank String name,
                                  @Email @NotBlank String email,
                                  @Size(min = 6) String password) {}

    public record LoginRequest(@Email @NotBlank String email,
                               @NotBlank String password) {}

    public record AuthResponse(String token, String name, String email, String role) {}

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        if (users.existsByEmail(req.email())) {
            throw new ResponseStatusException(CONFLICT, "email já cadastrado");
        }
        User u = new User();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setPassword(encoder.encode(req.password()));
        u.setRole("USER");
        users.save(u);
        return response(u);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        User u = users.findByEmail(req.email())
                .filter(x -> encoder.matches(req.password(), x.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "credenciais inválidas"));
        return response(u);
    }

    private AuthResponse response(User u) {
        return new AuthResponse(jwt.generate(u.getEmail(), u.getRole()),
                u.getName(), u.getEmail(), u.getRole());
    }
}
