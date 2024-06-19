package joao.dev.desafiobackendfcamara.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank(message = "Mandatory field")
                                String username,
                                @NotBlank(message = "Mandatory field")
                                String password) {
}
