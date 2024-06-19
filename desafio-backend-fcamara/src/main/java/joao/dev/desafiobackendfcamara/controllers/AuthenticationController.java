package joao.dev.desafiobackendfcamara.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import joao.dev.desafiobackendfcamara.domain.dtos.AuthenticationDTO;
import joao.dev.desafiobackendfcamara.domain.dtos.LoginResponseDTO;
import joao.dev.desafiobackendfcamara.domain.dtos.RegisterDTO;
import joao.dev.desafiobackendfcamara.domain.user.User;
import joao.dev.desafiobackendfcamara.infra.security.TokenService;
import joao.dev.desafiobackendfcamara.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Operation(description = "Login")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o token de acesso da aplicação",
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InN0cmluZyIsImV4cCI6MTcxNDE4Mjc0Nn0.6Zbq5QzYw0v7rZ2sY3QeR4KlZlBz9X8y5bJ5wYrYFqU"
                            )
                    )),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(description = "Cria um usuário")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário criado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByUsername(data.username()) != null) return ResponseEntity.badRequest()
                .body("Username already registered!");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        return new ResponseEntity<>(this.userRepository.save(newUser), HttpStatus.OK);
    }
}
