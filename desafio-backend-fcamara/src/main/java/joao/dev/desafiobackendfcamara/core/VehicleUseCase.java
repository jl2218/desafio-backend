package joao.dev.desafiobackendfcamara.core;

import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;

import java.util.List;

public interface VehicleUseCase {

    Vehicle createVehicle(VehicleDTO data);

    List<Vehicle> list();

    Vehicle updateVehicle(VehicleDTO data);

    String deleteVehicle(Long id) throws Exception;
}
