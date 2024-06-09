package joao.dev.desafiobackendfcamara.services;

import joao.dev.desafiobackendfcamara.core.CustomerUseCase;
import joao.dev.desafiobackendfcamara.domain.customer.Customer;
import joao.dev.desafiobackendfcamara.domain.dtos.CustomerDTO;
import joao.dev.desafiobackendfcamara.domain.dtos.VehicleDTO;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import joao.dev.desafiobackendfcamara.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService implements CustomerUseCase{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public Customer createCustomer(CustomerDTO data) {
        if (customerRepository.existsByDocument(data.document())) {
            throw new DataIntegrityViolationException("A customer with this document already exists");
        }
        Customer newCustomer = new Customer(data);
        Vehicle vehicle = vehicleService.saveOrFindByPlate(newCustomer.getVehicle());
        newCustomer.setVehicle(vehicle);
        this.saveCustomer(newCustomer);
        return newCustomer;
    }

    @Override
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(CustomerDTO data) {
        Customer customer = customerRepository.findById(data.id())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setName(data.name());
        customer.setDocument(data.document());
        customer.setPhoneNumber(data.phoneNumber());
        customer.setPeriod(data.period());
        customer.setContractCreationDate(LocalDate.now());

        this.saveCustomer(customer);
        return customer;
    }

    @Override
    public String deleteCustomer(String id) throws Exception {
        Customer customerToBeDeleted = customerRepository.findById(id)
                .orElseThrow(() -> new Exception("Customer not found"));

        customerRepository.delete(customerToBeDeleted);
        return "Customer deleted successfully";
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomerVehicle(String customerId, VehicleDTO data) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Vehicle vehicle = new Vehicle(data);
        vehicle = vehicleService.saveOrFindByPlate(vehicle);
        customer.setVehicle(vehicle);
        this.saveCustomer(customer);
        return customer;
    }

    public Customer findByDocument(String document) {
        return customerRepository.findByDocument(document)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }
}
