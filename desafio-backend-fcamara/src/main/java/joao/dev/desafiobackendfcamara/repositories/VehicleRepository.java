package joao.dev.desafiobackendfcamara.repositories;

import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicles, Long> {
}
