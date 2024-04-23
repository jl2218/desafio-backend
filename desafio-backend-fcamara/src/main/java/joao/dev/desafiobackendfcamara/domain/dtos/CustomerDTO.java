package joao.dev.desafiobackendfcamara.domain.dtos;

import joao.dev.desafiobackendfcamara.domain.customer.Period;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;

public record CustomerDTO(String id,
                          String document,
                          String name,
                          Vehicle vehicle,
                          String phoneNumber,
                          Period period) {
}
