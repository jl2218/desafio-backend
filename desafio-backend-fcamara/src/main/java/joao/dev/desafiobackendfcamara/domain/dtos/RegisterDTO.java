package joao.dev.desafiobackendfcamara.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import joao.dev.desafiobackendfcamara.domain.user.UserRole;

public record RegisterDTO(@NotBlank(message = "Mandatory field")
                          String username,
                          @NotBlank(message = "Mandatory field")
                          String password,
                          @NotNull(message = "Mandatory field")
                          UserRole role) {
}
