package joao.dev.desafiobackendfcamara.domain.dtos;

import joao.dev.desafiobackendfcamara.domain.user.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
