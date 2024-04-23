package joao.dev.desafiobackendfcamara.controllers;

import jakarta.validation.Valid;
import joao.dev.desafiobackendfcamara.domain.customer.Customer;
import joao.dev.desafiobackendfcamara.domain.dtos.CustomerDTO;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDTO data) {
        Customer newCustomer = customerService.createCustomer(data);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listCustomers() {
        return new ResponseEntity<>(customerService.list(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid CustomerDTO data) {
        Customer updatedCustomer = customerService.updateCustomer(data);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

    @PutMapping("/update/vehicle")
    public ResponseEntity<?> updateCustomerVehicle(@RequestParam String customerId,
                                                   @RequestBody @Valid VehicleDTO data) {
        Customer updatedCustomer = customerService.updateCustomerVehicle(customerId, data);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}
