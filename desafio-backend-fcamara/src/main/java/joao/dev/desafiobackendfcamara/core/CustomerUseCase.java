package joao.dev.desafiobackendfcamara.core;

import joao.dev.desafiobackendfcamara.domain.customer.Customer;
import joao.dev.desafiobackendfcamara.domain.dtos.CustomerDTO;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;

import java.util.List;

public interface CustomerUseCase {

    Customer createCustomer(CustomerDTO customerDTO);

    List<Customer> list();

    Customer updateCustomer(CustomerDTO customerDTO);

    String deleteCustomer(String id) throws Exception;

    void saveCustomer(Customer customer);

    Customer updateCustomerVehicle(String customerId, VehicleDTO vehicleDTO);
}
