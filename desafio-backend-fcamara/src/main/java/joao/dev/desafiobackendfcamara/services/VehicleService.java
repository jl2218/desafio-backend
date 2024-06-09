package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.VehicleUseCase;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService implements VehicleUseCase {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle createVehicle(VehicleDTO data) {
        if (vehicleRepository.existsByPlate(data.plate())) {
            throw new DataIntegrityViolationException("A vehicle with this plate already exists");
        }
        Vehicle newVehicle = new Vehicle(data);
        this.saveVehicle(newVehicle);
        return newVehicle;
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

        this.saveVehicle(vehicle);
        return vehicle;
    }

    @Override
    public String deleteVehicle(String id) throws Exception {
        Vehicle vehicleToBeDeleted = vehicleRepository.findById(id)
                .orElseThrow(() -> new Exception("Vehicle not found"));

        vehicleRepository.delete(vehicleToBeDeleted);
        return "Vehicle deleted successfully";
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle saveOrFindByPlate(Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findByPlate(vehicle.getPlate())
                .orElse(null);

        if (existingVehicle == null) {
            saveVehicle(vehicle);
            return vehicle;
        }

        return existingVehicle;
    }

    public Vehicle findByPlate(String plate) {
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
    }
}
