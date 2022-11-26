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

    @GetMapping("/rentals")
    List<Rental> getAll(){
        return repository.findAll();
    }
    @PostMapping("/rentals")
    Rental newRental(@RequestBody Rental newRental){
        return repository.save(newRental);
    }
    @GetMapping("/rentals/{id}")
    Optional<Rental> one(@PathVariable UUID id){
        return repository.findByid(id);
    }
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
                    return repository.save(rental)
                })
                .orElseGet(()->{
                    newRental.setId(id);
                    return repository.save(newRental);
                });

    }
    @DeleteMapping("/rentals/{id}")
    void delteRental(@PathVariable UUID id){
        repository.deleteById(id);
    }

}
