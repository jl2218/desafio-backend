package joao.dev.desafiobackendfcamara.domain.establishment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import joao.dev.desafiobackendfcamara.domain.address.Address;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.MovementType;
import joao.dev.desafiobackendfcamara.domain.vehicleMovements.VehicleMovements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "establishments")
@Table(name = "establishments")
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
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(unique = true)
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private int motorcycleParkingLots;
    @NotBlank
    private int carParkingLots;
    @OneToMany
    private List<Vehicle> parkedVehicles = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<VehicleMovements> entriesAndExits = new ArrayList<>();

    public Establishment(EstablishmentDTO data) {
        this.name = data.name();
        this.document = data.document();
        this.address = data.address();
        this.phoneNumber = data.phoneNumber();
        this.motorcycleParkingLots = data.motorcycleParkingLots();
        this.carParkingLots = data.carParkingLots();
    }

    public void addEntry() {
        VehicleMovements movement = new VehicleMovements(MovementType.ENTRY, LocalDateTime.now());
        this.getEntriesAndExits().add(movement);
    }

    public void addExit() {
        VehicleMovements movement = new VehicleMovements(MovementType.EXIT, LocalDateTime.now());
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

    private long filterByMovementTypeInLastHour(MovementType type) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return this.entriesAndExits.stream()
                .filter(vehicleMovements ->
                        vehicleMovements.getType() == type
                                && vehicleMovements.getMovementTime().isAfter(oneHourAgo))
                .count();
    }

    private long filterByMovementType(MovementType type) {
        return this.entriesAndExits.stream()
                .filter(vehicleMovements -> vehicleMovements.getType() == type)
                .count();
    }
}
