package joao.dev.desafiobackendfcamara.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import joao.dev.desafiobackendfcamara.domain.address.Address;

public record EstablishmentDTO(String id,
                               String name,
                               @NotBlank(message = "Mandatory field")
                               @Pattern(regexp = "^.{11}|.{14}$", message = "Format is invalid")
                               String document,
                               @NotNull(message = "Mandatory object")
                               Address address,
                               @NotBlank(message = "Mandatory field")
                               @Pattern(regexp = "\\d{10,11}", message = "Format is invalid")
                               String phoneNumber,
                               @NotNull(message = "Mandatory field")
                               Integer motorcycleParkingLots,
                               @NotNull(message = "Mandatory field")
                               Integer carParkingLots,
                               @NotNull(message = "Mandatory field")
                               Double valuePerHour) {
}
