package joao.dev.desafiobackendfcamara.services;

import jakarta.persistence.EntityNotFoundException;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.MovementType;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.VehicleMovements;
import joao.dev.desafiobackendfcamara.repositories.VehicleMovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VehicleMovementsService {

    @Autowired
    private VehicleMovementsRepository repository;

    public VehicleMovements processVehicleMovement(MovementType type, String vehiclePlate) {
        VehicleMovements movement = new VehicleMovements(type, vehiclePlate);
        if (type == MovementType.ENTRY) {
            movement.setPaid(false);
        }
        return repository.save(movement);
    }

    public VehicleMovements processEntryMovement(Establishment establishment, String vehiclePlate) {
        Optional<VehicleMovements> entryOpt = establishment.getEntriesAndExits().stream()
                .filter(entry -> entry.getType() == MovementType.ENTRY
                        && Objects.equals(entry.getVehiclePlate(), vehiclePlate)
                        && !entry.getPaid())
                .findFirst();

        if (entryOpt.isPresent()) {
            VehicleMovements entry = entryOpt.get();
            entry.setPaid(true);
            repository.save(entry);
            return entry;
        } else {
            throw new EntityNotFoundException("Entry not found");
        }
    }
}
