package joao.dev.desafiobackendfcamara.core;

import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;

import java.util.List;

public interface EstablishmentUseCase {

    Establishment createEstablishment(EstablishmentDTO establishmentDTO);

    List<Establishment> list();

    Establishment updateEstablishment(EstablishmentDTO establishmentDTO);

    String deleteEstablishment(Long id) throws Exception;
}
