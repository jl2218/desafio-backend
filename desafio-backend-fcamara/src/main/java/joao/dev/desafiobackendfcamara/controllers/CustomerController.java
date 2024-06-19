package joao.dev.desafiobackendfcamara.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Cria um cliente")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente criado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PostMapping("/register")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDTO data) {
        Customer newCustomer = customerService.createCustomer(data);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @Operation(description = "Lista todos os clientes")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os clientes"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @GetMapping("/list")
    public ResponseEntity<?> listCustomers() {
        return new ResponseEntity<>(customerService.list(), HttpStatus.OK);
    }


    @Operation(description = "Atualiza um cliente")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente atualizado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO data) {
        Customer updatedCustomer = customerService.updateCustomer(data);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @Operation(description = "Apaga um cliente")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna uma String de sucesso"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam String id) throws Exception {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

    @Operation(description = "Atualiza o veículo de um cliente")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Retorna o cliente atualizado"),
            @ApiResponse(responseCode = "500", description = "Retorna o erro específico")
    })
    @PutMapping("/update/vehicle")
    public ResponseEntity<?> updateCustomerVehicle(@RequestParam String customerId,
                                                   @RequestBody VehicleDTO data) {
        Customer updatedCustomer = customerService.updateCustomerVehicle(customerId, data);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}
