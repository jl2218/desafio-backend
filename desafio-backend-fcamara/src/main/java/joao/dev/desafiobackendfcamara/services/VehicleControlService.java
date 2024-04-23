package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.VehicleControlUseCase;
import joao.dev.desafiobackendfcamara.domain.customer.Customer;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;
import joao.dev.desafiobackendfcamara.repositories.CustomerRepository;
import joao.dev.desafiobackendfcamara.repositories.EstablishmentRepository;
import joao.dev.desafiobackendfcamara.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class VehicleControlService implements VehicleControlUseCase {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Establishment vehicleEntryControl(String establishmentDocument, String vehiclePlate) {
        Pair<Establishment, Vehicle> pair = getEstablishmentAndVehicle(establishmentDocument, vehiclePlate);
        Establishment establishment = pair.getFirst();
        Vehicle vehicle = pair.getSecond();

        return this.processVehicleControl(establishment, vehicle, true);
    }

    @Override
    public Establishment vehicleExitControl(String establishmentDocument, String vehiclePlate) {
        Pair<Establishment, Vehicle> pair = getEstablishmentAndVehicle(establishmentDocument, vehiclePlate);
        Establishment establishment = pair.getFirst();
        Vehicle vehicle = pair.getSecond();

        return this.processVehicleControl(establishment, vehicle, false);
    }

    @Override
    public String summary(String establishmentDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return "In the total period there were " + establishment.getEntries() + " entries and " + establishment.getExits() + " exits";
    }

    @Override
    public String summaryPerHour(String establishmentDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return "In the period of 1 hour there were " + establishment.getEntriesInLastHour() +
                " entries and " + establishment.getExitsInLastHour() + " exits";
    }

    @Override
    public Establishment vehicleEntryControlForCustomer(String establishmentDocument, String customerDocument) {
        Pair<Establishment, Customer> pair = getEstablishmentAndCustomer(establishmentDocument, customerDocument);
        Establishment establishment = pair.getFirst();
        Customer customer = pair.getSecond();
        customer.validateCustomerExpiration();

        return this.processVehicleControl(establishment, customer.getVehicle(), true);
    }

    @Override
    public Establishment vehicleExitControlForCustomer(String establishmentDocument, String customerDocument) {
        Pair<Establishment, Customer> pair = getEstablishmentAndCustomer(establishmentDocument, customerDocument);
        Establishment establishment = pair.getFirst();
        Customer customer = pair.getSecond();
        customer.validateCustomerExpiration();

        return this.processVehicleControl(establishment, customer.getVehicle(), false);
    }

    private Pair<Establishment, Vehicle> getEstablishmentAndVehicle(String establishmentDocument, String vehiclePlate) {
        Vehicle vehicle = vehicleRepository.findByPlate(vehiclePlate)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        return Pair.of(establishment, vehicle);
    }

    private Pair<Establishment, Customer> getEstablishmentAndCustomer(String establishmentDocument, String customerDocument) {
        Establishment establishment = establishmentRepository.findByDocument(establishmentDocument)
                .orElseThrow(() -> new IllegalArgumentException("Establishment not found"));
        Customer customer = customerRepository.findByDocument(customerDocument)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return Pair.of(establishment, customer);
    }

    private void updateParkingLots(Establishment establishment, Vehicle vehicle, int increment) {
        if (vehicle.getType().equals(VehicleType.CAR)) {
            establishment.setCarParkingLots(establishment.getCarParkingLots() + increment);
        } else {
            establishment.setMotorcycleParkingLots(establishment.getMotorcycleParkingLots() + increment);
        }
    }

    public Establishment processVehicleControl(Establishment establishment, Vehicle vehicle, boolean isEntry) {
        boolean isVehicleParked = establishment.getParkedVehicles().contains(vehicle);
        if (isEntry && isVehicleParked) {
            throw new IllegalArgumentException("This vehicle is already parked!");
        } else if (!isEntry && !isVehicleParked) {
            throw new IllegalArgumentException("There is no vehicle with this plate parked!");
        }

        if (isEntry) {
            this.validateParkingLotsAvailable(establishment, vehicle);
            establishment.getParkedVehicles().add(vehicle);
            updateParkingLots(establishment, vehicle, -1);
            establishment.addEntry();
        } else {
            establishment.getParkedVehicles().remove(vehicle);
            updateParkingLots(establishment, vehicle, 1);
            establishment.addExit();
        }

        establishmentService.saveEstablishment(establishment);

        return establishment;
    }

    private void validateParkingLotsAvailable(Establishment establishment, Vehicle vehicle) {
        if (vehicle.getType().equals(VehicleType.CAR)) {
            if (establishment.getCarParkingLots() == 0) {
                throw new IllegalArgumentException("There is no more parking lots available!");
            }
        } else {
            if (establishment.getMotorcycleParkingLots() == 0) {
                throw new IllegalArgumentException("There is no more parking lots available!");
            }
        }
    }
}
