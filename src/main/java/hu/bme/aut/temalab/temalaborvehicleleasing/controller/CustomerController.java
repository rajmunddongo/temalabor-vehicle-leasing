package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

import hu.bme.aut.temalab.temalaborvehicleleasing.controller.model.CreateCustomer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    /**
     * Gives back all the customers present in the database
     *
     * @return ResponseEntity containing the result list of customers (if the process was successful) and a HTTP status code
     */
    @GetMapping("/customers/all")
    public ResponseEntity<List<Customer>> findAll() {
        
    	List<Customer> allCustomers;

        try {
            allCustomers = customerRepository.findAll();

            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Creates a new customer and saves it in the database
     *
     * @param createCustomer the new customer that should be saved
     * @return ResponseEntity containing the freshly added customer (if the process was successful) and a HTTP status code
     */
    @PostMapping("/customers/save")
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomer createCustomer) {
        
    	try {
            return new ResponseEntity<>(customerService.createCustomer(createCustomer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Tries to find a customer in the database based on the given ID
     *
     * @param id the ID that can identify the desired customer
     * @return ResponseEntity containing the found customer (if it was found) and a HTTP status code
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<Optional<Customer>> findCustomerById(@PathVariable UUID id) {
        
    	Optional<Customer> customer;

        try {
            customer = customerRepository.findById(id);

            if (customer.isPresent()) {
                return new ResponseEntity<>(customer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an already present customers's data
     *
     * @param newCustomer Customer object that contains the new data
     * @param id          the ID of the customer whose data needs to be changed
     * @return ResponseEntity containing the updated customer (if the process was successful) and a HTTP status code
     */
    @PostMapping("/customers/{id}/update")
    public ResponseEntity<Customer> updateCustomersData(@RequestBody Customer newCustomer, @PathVariable UUID id) {

        Optional<Customer> oldCustomer;

        try {
            oldCustomer = customerRepository.findById(id);

            if (oldCustomer.isPresent()) {
                oldCustomer.get().setFirstName(newCustomer.getFirstName());
                oldCustomer.get().setLastName(newCustomer.getLastName());
                oldCustomer.get().setBonusPoints(newCustomer.getBonusPoints());
                oldCustomer.get().setRentals(newCustomer.getRentals());
                oldCustomer.get().setDrivingLicenceNumber(newCustomer.getDrivingLicenceNumber());
                oldCustomer.get().setPassword(newCustomer.getPassword());
                oldCustomer.get().setUsername(newCustomer.getUsername());

                return new ResponseEntity<>(customerRepository.save(oldCustomer.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes a customer from the database
     *
     * @param id identifies the customer that have to be deleted
     * @return ResponseEntity containing a HTTP status code indicating the process's result
     */
    @DeleteMapping("/customers/{id}/delete")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable UUID id) {

        try {
            customerRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
