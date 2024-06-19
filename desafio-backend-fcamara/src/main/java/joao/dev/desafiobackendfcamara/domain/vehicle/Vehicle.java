package joao.dev.desafiobackendfcamara.domain.vehicle;

import jakarta.persistence.*;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vehicle {

    @Id
    private String id;
    private String model;
    private String color;
    @EqualsAndHashCode.Include
    private String plate;
    private VehicleType type;

    public Vehicle(VehicleDTO data) {
        this.model = data.model();
        this.color = data.color();
        this.plate = data.plate();
        this.type = data.type();
    }
}
