package joao.dev.desafiobackendfcamara.domain.establishment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import joao.dev.desafiobackendfcamara.domain.address.Address;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "establishments")
@Table(name = "establishments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
    private int exits;

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
    }

    public void addExit() {
        int exits = this.exits + 1;
        this.setExits(exits);
    }
}
