package joao.dev.desafiobackendfcamara.domain.dtos;

import joao.dev.desafiobackendfcamara.domain.address.Address;

public record EstablishmentDTO(Long id,
                               String name,
                               String document,
                               Address address,
                               String phoneNumber,
                               int motorcycleParkingLots,
                               int carParkingLots) {
}
