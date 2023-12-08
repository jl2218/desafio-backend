package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.VehicleUseCase;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements VehicleUseCase {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle createVehicle(VehicleDTO data) {
        Vehicle newVehicle = new Vehicle(data);
        return vehicleRepository.save(newVehicle);
    }

    @Override
    public List<Vehicle> list() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle updateVehicle(VehicleDTO data) {
        Vehicle vehicle = vehicleRepository.findById(data.id())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        vehicle.setModel(data.model());
        vehicle.setColor(data.color());
        vehicle.setPlate(data.plate());
        vehicle.setType(data.type());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public String deleteVehicle(Long id) {
        Vehicle vehicleToBeDeleted = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        try {
            vehicleRepository.delete(vehicleToBeDeleted);
            return "Vehicle deleted successfully";
        } catch (Exception e) {
            return "Error deleting the vehicle";
        }
    }
}
