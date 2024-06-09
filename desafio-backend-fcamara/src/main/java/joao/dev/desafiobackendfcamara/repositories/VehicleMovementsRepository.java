package joao.dev.desafiobackendfcamara.repositories;

import joao.dev.desafiobackendfcamara.domain.vehicleMovements.VehicleMovements;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleMovementsRepository extends MongoRepository<VehicleMovements, String> {
}
