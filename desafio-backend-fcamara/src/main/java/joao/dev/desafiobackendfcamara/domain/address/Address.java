package joao.dev.desafiobackendfcamara.domain.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private int number;

}
