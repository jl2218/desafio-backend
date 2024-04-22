package joao.dev.desafiobackendfcamara.domain.dtos;

import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;

public record VehicleDTO(String id,
                         String model,
                         String color,
                         String plate,
                         VehicleType type) {
}
