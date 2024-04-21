package joao.dev.desafiobackendfcamara.domain.establishment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import joao.dev.desafiobackendfcamara.domain.address.Address;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
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
    private int entries;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime entryTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int exits;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime exitTime;

    public Establishment(EstablishmentDTO data) {
        this.name = data.name();
        this.document = data.document();
        this.address = data.address();
        this.phoneNumber = data.phoneNumber();
        this.motorcycleParkingLots = data.motorcycleParkingLots();
        this.carParkingLots = data.carParkingLots();
    }

    public void addEntry() {
        int entries = this.entries + 1;
        this.setEntries(entries);
        this.entryTime = LocalDateTime.now();
    }

    public void addExit() {
        int exits = this.exits + 1;
        this.setExits(exits);
        this.exitTime = LocalDateTime.now();
    }

    @JsonIgnore
    public int getEntriesInLastHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        if (this.entryTime != null && this.entryTime.isAfter(oneHourAgo)) {
            return this.entries;
        } else {
            return 0;
        }
    }

    @JsonIgnore
    public int getExitsInLastHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        if (this.exitTime != null && this.exitTime.isAfter(oneHourAgo)) {
            return this.exits;
        } else {
            return 0;
        }
    }
}
