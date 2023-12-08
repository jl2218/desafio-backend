package joao.dev.desafiobackendfcamara.domain.establishment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import joao.dev.desafiobackendfcamara.domain.address.Address;
import joao.dev.desafiobackendfcamara.domain.dtos.EstablishmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private int motorcycleParkingLots;
    @NotBlank
    private int carParkingLots;

    public Establishment(EstablishmentDTO data) {
        this.name = data.name();
        this.document = data.document();
        this.address = data.address();
        this.phoneNumber = data.phoneNumber();
        this.motorcycleParkingLots = data.motorcycleParkingLots();
        this.carParkingLots = data.carParkingLots();
    }
}
