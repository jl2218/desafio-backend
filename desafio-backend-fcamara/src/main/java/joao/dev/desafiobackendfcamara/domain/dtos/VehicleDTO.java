package joao.dev.desafiobackendfcamara.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;

public record VehicleDTO(String id,
                         @NotBlank(message = "Mandatory field")
                         String model,
                         String color,
                         @NotBlank(message = "Mandatory field")
                         @Size(max = 7, message = "Format invalid")
                         String plate,
                         @NotNull(message = "Mandatory field")
                         VehicleType type) {
}
