package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.VehicleControlUseCase;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;
import joao.dev.desafiobackendfcamara.repositories.EstablishmentRepository;
import joao.dev.desafiobackendfcamara.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleControlService implements VehicleControlUseCase {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Override
    public Establishment vehicleEntryControl(String establishmentDocument, String vehiclePlate) {
        Vehicle vehicle = vehicleRepository.findByPlate(vehiclePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        establishment.getParkedVehicles().add(vehicle);
        if (vehicle.getType().equals(VehicleType.CAR)) {
            establishment.setCarParkingLots(establishment.getCarParkingLots() -1);
        } else {
            establishment.setMotorcycleParkingLots(establishment.getMotorcycleParkingLots() -1);
        }
        establishment.addEntry();
        establishmentRepository.save(establishment);
        return establishment;
    }

    @Override
    public Establishment vehicleExitControl(String establishmentDocument, String vehiclePlate) {
        Vehicle vehicle = vehicleRepository.findByPlate(vehiclePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        establishment.getParkedVehicles().remove(vehicle);
        if (vehicle.getType().equals(VehicleType.CAR)) {
            establishment.setCarParkingLots(establishment.getCarParkingLots() + 1);
        } else {
            establishment.setMotorcycleParkingLots(establishment.getMotorcycleParkingLots() + 1);
        }
        establishment.addExit();
        establishmentRepository.save(establishment);
        return establishment;
    }

    @Override
    public String entriesSummary(String establishmentDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return "No período total foram contabilizadas " + establishment.getEntries() + " entradas";
    }

    @Override
    public String exitsSummary(String establishmentDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return "No período total foram contabilizadas " + establishment.getExits() + " saídas";
    }
}
