package joao.dev.desafiobackendfcamara.domain.establishment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstablishmentAndDebits {

    private Establishment establishment;
    private double debits;
    private long parkedHours;

    public static EstablishmentAndDebits getEstablishmentAndDebits(Establishment establishment,
                                                                   double debits,
                                                                   long parkedHours) {
        return EstablishmentAndDebits.builder()
                .establishment(establishment)
                .debits(debits)
                .parkedHours(parkedHours)
                .build();
    }
}
