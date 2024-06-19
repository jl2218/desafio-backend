package joao.dev.desafiobackendfcamara.domain.customer;

import jakarta.persistence.*;
import joao.dev.desafiobackendfcamara.domain.dtos.CustomerDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String id;
    private String document;
    private String name;
    private Vehicle vehicle;
    private String phoneNumber;
    private Period period;
    private LocalDate contractCreationDate;
    private boolean expired;

    public Customer(CustomerDTO data) {
        this.document = data.document();
        this.name = data.name();
        this.vehicle = data.vehicle();
        this.phoneNumber = data.phoneNumber();
        this.period = data.period();
        this.contractCreationDate = LocalDate.now();
        this.expired = false;
    }

    public void validateCustomerExpiration() {
        this.validateExpirationPeriod();
        if (this.isExpired()) {
            throw new DataIntegrityViolationException("This customer's contract has expired, please update it!");
        }
    }

    public void validateExpirationPeriod() {
        LocalDate now = LocalDate.now();
        if (this.getPeriod().equals(Period.MONTHLY)) {
            LocalDate oneMonthAgo = now.minusMonths(1);
            if (contractCreationDate.isBefore(oneMonthAgo)) this.setExpired(true);
        } else if (this.getPeriod().equals(Period.SEMESTER)) {
            LocalDate sixMonthsAgo = now.minusMonths(6);
            if (contractCreationDate.isBefore(sixMonthsAgo)) this.setExpired(true);
        } else if(this.getPeriod().equals(Period.ANNUALLY)) {
            LocalDate oneYearAgo = now.minusYears(1);
            if (contractCreationDate.isBefore(oneYearAgo)) this.setExpired(true);
        }
    }
}
