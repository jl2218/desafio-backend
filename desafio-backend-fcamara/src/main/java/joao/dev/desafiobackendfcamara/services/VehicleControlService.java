package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.VehicleControlUseCase;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;
import joao.dev.desafiobackendfcamara.repositories.EstablishmentRepository;
import joao.dev.desafiobackendfcamara.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleControlService implements VehicleControlUseCase {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private EstablishmentService establishmentService;

    @Override
    public Establishment vehicleEntryControl(String establishmentDocument, String vehiclePlate) {
        Pair<Establishment, Vehicle> pair = getEstablishmentAndVehicle(establishmentDocument, vehiclePlate);
        Establishment establishment = pair.getFirst();
        Vehicle vehicle = pair.getSecond();
        establishment.getParkedVehicles().add(vehicle);
        updateParkingLots(establishment, vehicle, -1);
        establishment.addEntry();
        establishmentService.saveEstablishment(establishment);
        return establishment;
    }

    @Override
    public Establishment vehicleExitControl(String establishmentDocument, String vehiclePlate) {
        Pair<Establishment, Vehicle> pair = getEstablishmentAndVehicle(establishmentDocument, vehiclePlate);
        Establishment establishment = pair.getFirst();
        Vehicle vehicle = pair.getSecond();
        establishment.getParkedVehicles().remove(vehicle);
        updateParkingLots(establishment, vehicle, 1);
        establishment.addExit();
        establishmentService.saveEstablishment(establishment);
        return establishment;
    }

    @Override
    public String summary(String establishmentDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return "No período total foram contabilizadas " + establishment.getEntries() + " entradas e " + establishment.getExits() + " saídas";
    }

    @Override
    public String summaryPerHour(String establishmentDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return "No período de 1 hora foram contabilizadas " + establishment.getEntriesInLastHour() +
                " entradas e " + establishment.getExitsInLastHour() + " saídas";
    }

    private Pair<Establishment, Vehicle> getEstablishmentAndVehicle(String establishmentDocument, String vehiclePlate) {
        Vehicle vehicle = vehicleRepository.findByPlate(vehiclePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return Pair.of(establishment, vehicle);
    }

    private void updateParkingLots(Establishment establishment, Vehicle vehicle, int increment) {
        if (vehicle.getType().equals(VehicleType.CAR)) {
            establishment.setCarParkingLots(establishment.getCarParkingLots() + increment);
        } else {
            establishment.setMotorcycleParkingLots(establishment.getMotorcycleParkingLots() + increment);
        }
    }
}
