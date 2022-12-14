package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RentalController {
    RentalRepository repository;
    /**
     * Gives back all the rentals present in the database
     *
     * @return ResponseEntity containing the result list of rentals (if the process was successful) and an HTTP status code
     */
    @GetMapping("/rentals")
    List<Rental> getAll(){
        return repository.findAll();
    }
    /**
     * Creates a new rental and saves it in the database
     *
     * @param newRental the new rental that should be saved
     * @return ResponseEntity containing the freshly added rental (if the process was successful) and an HTTP status code
     */
    @PostMapping("/rentals")
    Rental newRental(@RequestBody Rental newRental){
        return repository.save(newRental);
    }
    /**
     * Tries to find a rental in the database based on the given ID
     *
     * @param id the ID that can identify the desired customer
     * @return ResponseEntity containing the found rental (if it was found) and an HTTP status code
     */
    @GetMapping("/rentals/{id}")
    Optional<Rental> one(@PathVariable UUID id){
        return repository.findByid(id);
    }
    /**
     * Updates an already present rental's data
     *
     * @param newRental Rental object that contains the new data
     * @param id the ID of the rental whose data needs to be changed
     * @return ResponseEntity containing the updated rental (if the process was successful) and an HTTP status code
     */
    @PutMapping("/rentals/{id}")
    Rental replaceRental(@RequestBody Rental newRental,@PathVariable UUID id){
        return repository.findByid(id)
                .map(rental -> {
                    rental.setCustomer(newRental.getCustomer());
                    rental.setPrice(newRental.getPrice());
                    rental.setVehicle(newRental.getVehicle());
                    rental.setEndDate(newRental.getEndDate());
                    rental.setStartDate(newRental.getStartDate());
                    rental.setUseKm(newRental.getUseKm());
                    return repository.save(rental);
                })
                .orElseGet(()->{
                    newRental.setId(id);
                    return repository.save(newRental);
                });

    }
    /**
     * Removes a rental from the database
     *
     * @param id identifies the rental that have to be deleted
     */
    @DeleteMapping("/rentals/{id}")
    void deleteRental(@PathVariable UUID id){
        repository.deleteById(id);
    }

}
