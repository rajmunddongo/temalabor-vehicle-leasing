package hu.bme.aut.temalab.temalaborvehicleleasing.controller;


import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    @GetMapping("/vehicles")
    List<Vehicle> all(){
        return vehicleRepository.findAll();
    }
    @PostMapping("/vehicles")
    Vehicle newEmployee(@RequestBody Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }
    @GetMapping("/vehicles/{id}")
    Optional<Vehicle> findVehicle (@PathVariable UUID id){
        return vehicleRepository.findById(id);
    }
    @PutMapping("/vehicles/{id}")
    Vehicle replaceVehicle(@RequestBody Vehicle newVehicle,@PathVariable UUID id){
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setVehicleType(newVehicle.getVehicleType());
                    vehicle.setDescription(newVehicle.getDescription());
                    vehicle.setMileage(newVehicle.getMileage());
                    vehicle.setSeats(newVehicle.getSeats());
                    vehicle.setFuelType(newVehicle.getFuelType());
                    vehicle.setHorsePower(newVehicle.getHorsePower());
                    return vehicleRepository.save(newVehicle);
                })
                .orElseGet(()->{
                    newVehicle.setId(id);
                    return vehicleRepository.save(newVehicle);
                });
    }
    @DeleteMapping("/vehicles/{id}")
    void deleteVehicle(@PathVariable UUID id){
        vehicleRepository.deleteById(id);
    }

}
