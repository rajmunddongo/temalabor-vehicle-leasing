package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class CustomerController {
    CustomerRepository repository;

    @GetMapping("/customers")
    List<Customer> findall(){
        return  repository.findAll();
    }
    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newCustomer){
        return repository.save(newCustomer);
    }
    @GetMapping("/customers/{id}")
    Optional<Customer> onlyone(@PathVariable UUID id){
        return repository.findById(id);
    }
    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer,@PathVariable UUID id){
        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setBonusPoints(newCustomer.getBonusPoints());
                    customer.setRentals(newCustomer.getRentals());
                    customer.setDrivingLicenceNumber(newCustomer.getDrivingLicenceNumber());
                    customer.setPassword(newCustomer.getPassword());
                    customer.setUsername(newCustomer.getUsername());
                    return repository.save(customer);
                })
                .orElseGet(()-> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                        }
                );
    }
    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable UUID id){
        repository.deleteById(id);
    }
}
