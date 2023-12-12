package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.EstablishmentUseCase;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablishmentService implements EstablishmentUseCase {

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Override
    public Establishment createEstablishment(EstablishmentDTO establishmentDTO) {
        if (establishmentRepository.existsByDocument(establishmentDTO.document())) {
            throw new DataIntegrityViolationException("An establishment with this document already exists");
        }
        Establishment newEstablishment = new Establishment(establishmentDTO);
        return establishmentRepository.save(newEstablishment);
    }

    @Override
    public List<Establishment> list() {
        return establishmentRepository.findAll();
    }

    @Override
    public Establishment updateEstablishment(EstablishmentDTO establishmentDTO) {
        Establishment establishment = establishmentRepository.findById(establishmentDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));

        establishment.setName(establishmentDTO.name());
        establishment.setDocument(establishmentDTO.document());
        establishment.setAddress(establishmentDTO.address());
        establishment.setPhoneNumber(establishmentDTO.phoneNumber());
        establishment.setMotorcycleParkingLots(establishmentDTO.motorcycleParkingLots());
        establishment.setCarParkingLots(establishmentDTO.carParkingLots());

        return establishmentRepository.save(establishment);
    }


    @Override
    public String deleteEstablishment(Long id) throws Exception {
        Establishment establishmentToBeDeleted = establishmentRepository.findById(id)
                .orElseThrow(() -> new Exception("Establishment not found"));

        establishmentRepository.delete(establishmentToBeDeleted);
        return "Establishment deleted successfully";
    }
}
