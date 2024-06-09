package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.VehicleControlUseCase;
import joao.dev.desafiobackendfcamara.domain.customer.Customer;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.establishment.EstablishmentAndDebits;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.MovementType;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.VehicleMovements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class VehicleControlService implements VehicleControlUseCase {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VehicleMovementsService vehicleMovementsService;
    @Autowired
    private EstablishmentService establishmentService;

    @Override
    public Establishment vehicleEntryControl(String establishmentDocument, String vehiclePlate) {
        Pair<Establishment, Vehicle> pair = getEstablishmentAndVehicle(establishmentDocument, vehiclePlate);
        Establishment establishment = pair.getFirst();
        Vehicle vehicle = pair.getSecond();

        return this.processVehicleEntry(establishment, vehicle);
    }

    @Override
    public EstablishmentAndDebits vehicleExitControl(String establishmentDocument, String vehiclePlate) {
        Pair<Establishment, Vehicle> pair = getEstablishmentAndVehicle(establishmentDocument, vehiclePlate);
        Establishment establishment = pair.getFirst();
        Vehicle vehicle = pair.getSecond();

        establishment = this.processVehicleExit(establishment, vehicle);
        VehicleMovements entry = vehicleMovementsService.processEntryMovement(establishment, vehiclePlate);
        long parkedHours = establishment.calculateHours(entry);
        double debits = establishment.calculateDebits(entry);

        return EstablishmentAndDebits.getEstablishmentAndDebits(establishment, debits, parkedHours);
    }

    @Override
    public String summary(String establishmentDocument) {
        Establishment establishment = establishmentService.findByDocument(establishmentDocument);
        return "In the total period there were " + establishment.getEntries() + " entries and " + establishment.getExits() + " exits";
    }

    @Override
    public String summaryPerHour(String establishmentDocument) {
        Establishment establishment = establishmentService.findByDocument(establishmentDocument);
        return "In the period of 1 hour there were " + establishment.getEntriesInLastHour() +
                " entries and " + establishment.getExitsInLastHour() + " exits";
    }

    @Override
    public Establishment vehicleEntryControlForCustomer(String establishmentDocument, String customerDocument) {
        Pair<Establishment, Customer> pair = getEstablishmentAndCustomer(establishmentDocument, customerDocument);
        Establishment establishment = pair.getFirst();
        Customer customer = pair.getSecond();
        customer.validateCustomerExpiration();

        return this.processVehicleEntry(establishment, customer.getVehicle());
    }

    @Override
    public Establishment vehicleExitControlForCustomer(String establishmentDocument, String customerDocument) {
        Pair<Establishment, Customer> pair = getEstablishmentAndCustomer(establishmentDocument, customerDocument);
        Establishment establishment = pair.getFirst();
        Customer customer = pair.getSecond();
        customer.validateCustomerExpiration();

        return this.processVehicleExit(establishment, customer.getVehicle());
    }

    private Pair<Establishment, Vehicle> getEstablishmentAndVehicle(String establishmentDocument, String vehiclePlate) {
        Vehicle vehicle = vehicleService.findByPlate(vehiclePlate);
        Establishment establishment = establishmentService.findByDocument(establishmentDocument);
        return Pair.of(establishment, vehicle);
    }

    private Pair<Establishment, Customer> getEstablishmentAndCustomer(String establishmentDocument, String customerDocument) {
        Establishment establishment = establishmentService.findByDocument(establishmentDocument);
        Customer customer = customerService.findByDocument(customerDocument);
        return Pair.of(establishment, customer);
    }

    public Establishment processVehicleEntry(Establishment establishment, Vehicle vehicle) {
        boolean isVehicleParked = establishment.getParkedVehicles().contains(vehicle);
        if (isVehicleParked) {
            throw new IllegalArgumentException("This vehicle is already parked!");
        }

        establishment.validateParkingLotsAvailable(vehicle.getType());
        establishment.getParkedVehicles().add(vehicle);
        establishment.updateParkingLots(vehicle.getType(), -1);
        establishment.addEntryOrExit(vehicleMovementsService.processVehicleMovement(MovementType.ENTRY, vehicle.getPlate()));

        establishmentService.saveEstablishment(establishment);

        return establishment;
    }

    public Establishment processVehicleExit(Establishment establishment, Vehicle vehicle) {
        boolean isVehicleParked = establishment.getParkedVehicles().contains(vehicle);
        if (!isVehicleParked) {
            throw new IllegalArgumentException("There is no vehicle with this plate parked!");
        }

        establishment.getParkedVehicles().remove(vehicle);
        establishment.updateParkingLots(vehicle.getType(), 1);
        establishment.addEntryOrExit(vehicleMovementsService.processVehicleMovement(MovementType.EXIT, vehicle.getPlate()));

        establishmentService.saveEstablishment(establishment);

        return establishment;
    }
}
