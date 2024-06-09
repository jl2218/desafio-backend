package joao.dev.desafiobackendfcamara.domain.dtos;

import joao.dev.desafiobackendfcamara.domain.address.Address;

public record EstablishmentDTO(String id,
                               String name,
                               String document,
                               Address address,
                               String phoneNumber,
                               int motorcycleParkingLots,
                               int carParkingLots,
                               double valuePerHour) {
}
