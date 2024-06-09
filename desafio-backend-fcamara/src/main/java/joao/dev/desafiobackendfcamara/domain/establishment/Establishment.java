package joao.dev.desafiobackendfcamara.domain.establishment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import joao.dev.desafiobackendfcamara.domain.address.Address;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.domain.vehicle.VehicleType;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.MovementType;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.VehicleMovements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {

    @Id
    private String id;
    @NotBlank
    private String name;
    @Column(unique = true)
    @NotBlank
    private String document;
    @NotBlank
    private Address address;
    @Column(unique = true)
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private int motorcycleParkingLots;
    @NotBlank
    private int carParkingLots;
    private List<Vehicle> parkedVehicles = new ArrayList<>();
    @NotBlank
    private double valuePerHour;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DBRef
    private List<VehicleMovements> entriesAndExits = new ArrayList<>();

    public Establishment(EstablishmentDTO data) {
        this.name = data.name();
        this.document = data.document();
        this.address = data.address();
        this.phoneNumber = data.phoneNumber();
        this.motorcycleParkingLots = data.motorcycleParkingLots();
        this.carParkingLots = data.carParkingLots();
        this.valuePerHour = data.valuePerHour();
    }

    public void updateEstablishment(EstablishmentDTO data) {
        this.name = data.name();
        this.document = data.document();
        this.address = data.address();
        this.phoneNumber = data.phoneNumber();
        this.motorcycleParkingLots = data.motorcycleParkingLots();
        this.carParkingLots = data.carParkingLots();
        this.valuePerHour = data.valuePerHour();
    }

    public void addEntryOrExit(VehicleMovements movement) {
        this.getEntriesAndExits().add(movement);
    }

    @JsonIgnore
    public long getEntriesInLastHour() {
        return this.filterByMovementTypeInLastHour(MovementType.ENTRY);
    }

    @JsonIgnore
    public long getExitsInLastHour() {
        return this.filterByMovementTypeInLastHour(MovementType.EXIT);
    }

    @JsonIgnore
    public long getEntries() {
        return this.filterByMovementType(MovementType.ENTRY);
    }

    @JsonIgnore
    public long getExits() {
        return this.filterByMovementType(MovementType.EXIT);
    }

    @JsonIgnore
    private long filterByMovementTypeInLastHour(MovementType type) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return this.entriesAndExits.stream()
                .filter(vehicleMovements ->
                        vehicleMovements.getType() == type
                                && vehicleMovements.getMovementTime().isAfter(oneHourAgo))
                .count();
    }

    @JsonIgnore
    private long filterByMovementType(MovementType type) {
        return this.entriesAndExits.stream()
                .filter(vehicleMovements -> vehicleMovements.getType() == type)
                .count();
    }

    public void validateParkingLotsAvailable(VehicleType vehicleType) {
        if (vehicleType.equals(VehicleType.CAR)) {
            if (this.carParkingLots == 0) {
                throw new IllegalArgumentException("There is no more parking lots available!");
            }
        } else if (vehicleType.equals(VehicleType.MOTORCYCLE)) {
            if (this.motorcycleParkingLots == 0) {
                throw new IllegalArgumentException("There is no more parking lots available!");
            }
        }
    }

    public void updateParkingLots(VehicleType vehicleType, int increment) {
        if (vehicleType.equals(VehicleType.CAR)) {
            this.carParkingLots = this.carParkingLots + increment;
        } else if (vehicleType.equals(VehicleType.MOTORCYCLE)) {
            this.motorcycleParkingLots = this.motorcycleParkingLots + increment;
        }
    }

    @JsonIgnore
    public long calculateHours(VehicleMovements entry) {
        LocalDateTime now = LocalDateTime.now().minusHours(3);

        return Duration.between(entry.getMovementTime(), now).toHours();
    }

    @JsonIgnore
    public double calculateDebits(VehicleMovements entry) {
        long parkedHours = calculateHours(entry);
        if (parkedHours == 0) {
            return 1 * this.valuePerHour;
        }
        return parkedHours * this.valuePerHour;
    }
}
