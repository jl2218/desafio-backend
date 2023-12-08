package joao.dev.desafiobackendfcamara.domain.dtos;

import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;

public record VehicleDTO(Long id,
                         String model,
                         String color,
                         String plate,
                         VehicleType type) {
}
