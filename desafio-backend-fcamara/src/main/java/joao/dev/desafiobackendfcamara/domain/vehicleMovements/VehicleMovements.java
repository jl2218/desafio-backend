package joao.dev.desafiobackendfcamara.domain.vehicleMovements;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("movements")
public class VehicleMovements {

    @Id
    private String id;
    private MovementType type;
    private LocalDateTime movementTime;
    private String vehiclePlate;
    private Boolean paid;

    public VehicleMovements(MovementType type, String vehiclePlate) {
        this.type = type;
        this.movementTime = LocalDateTime.now().minusHours(3);
        this.vehiclePlate = vehiclePlate;
    }
}
