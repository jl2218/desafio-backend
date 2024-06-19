package joao.dev.desafiobackendfcamara.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import joao.dev.desafiobackendfcamara.domain.customer.Period;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;

public record CustomerDTO(String id,
                          @NotBlank(message = "Mandatory field")
                          @Pattern(regexp = "^.{11}|.{14}$", message = "Format is invalid")
                          String document,
                          String name,
                          @NotNull(message = "Mandatory object")
                          Vehicle vehicle,
                          @NotBlank(message = "Mandatory field")
                          @Pattern(regexp = "\\d{10,11}", message = "Format is invalid")
                          String phoneNumber,
                          @NotNull(message = "Mandatory field")
                          Period period) {
}
