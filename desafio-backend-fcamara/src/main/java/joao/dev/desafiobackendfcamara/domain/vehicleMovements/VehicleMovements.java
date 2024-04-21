package joao.dev.desafiobackendfcamara.domain.vehicleMovements;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMovements {

    private MovementType type;
    private LocalDateTime movementTime;
}
