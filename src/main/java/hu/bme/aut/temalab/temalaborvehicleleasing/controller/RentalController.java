package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class RentalController {
    @Autowired
    private RentalRepository rentalRepository;

    /**
     * Gives back all the rentals present in the database
     *
     * @return ResponseEntity containing the result list of rentals (if the process was successful) and an HTTP status code
     */
    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getAll() {
        List<Rental> allCustomers;
        try {
            allCustomers = rentalRepository.findAll();

            return new ResponseEntity<>(allCustomers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new rental and saves it in the database
     *
     * @param newRental the new rental that should be saved
     * @return ResponseEntity containing the freshly added rental (if the process was successful) and an HTTP status code
     */
    @PostMapping("/rentals/save")
    public ResponseEntity<Rental> newRental(@RequestBody Rental newRental) {
        Rental rental;
        try {
            rental = rentalRepository.save(newRental);

            return new ResponseEntity<>(rental, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Tries to find a rental in the database based on the given ID
     *
     * @param id the ID that can identify the desired customer
     * @return ResponseEntity containing the found rental (if it was found) and an HTTP status code
     */
    @GetMapping("/rentals/{id}")
    public ResponseEntity<Optional<Rental>> one(@PathVariable UUID id) {
        Optional<Rental> rental;

        try {
            rental = rentalRepository.findById(id);

            if (rental.isPresent()) {
                return new ResponseEntity<>(rental, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an already present rental's data
     *
     * @param newRental Rental object that contains the new data
     * @param id        the ID of the rental whose data needs to be changed
     * @return ResponseEntity containing the updated rental (if the process was successful) and an HTTP status code
     */
    @PostMapping("/rentals/{id}/update")
    public ResponseEntity<Rental> replaceRental(@RequestBody Rental newRental, @PathVariable UUID id) {
        try {
            if (rentalRepository.findById(id).isPresent()) {
                rentalRepository.findById(id)
                        .map(rental -> {
                            rental.setCustomer(newRental.getCustomer());
                            rental.setPrice(newRental.getPrice());
                            rental.setVehicle(newRental.getVehicle());
                            rental.setEndDate(newRental.getEndDate());
                            rental.setStartDate(newRental.getStartDate());
                            rental.setUseKm(newRental.getUseKm());
                            return new ResponseEntity<>(rentalRepository.save(rental), HttpStatus.OK);
                        });
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Removes a rental from the database
     *
     * @param id identifies the rental that have to be deleted
     */
    @DeleteMapping("/rentals/{id}/delete")
    public ResponseEntity<HttpStatus> deleteRental(@PathVariable UUID id) {
        try {
            rentalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
