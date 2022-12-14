package hu.bme.aut.temalab.temalaborvehicleleasing.controller;


import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
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
public class VehicleController {

    @Autowired
    private final VehicleRepository vehicleRepository;

    @GetMapping("/vehicles")
    ResponseEntity<List<Vehicle>> all(){
        try{
            return new ResponseEntity<>(vehicleRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/vehicles")
    ResponseEntity<Vehicle> newEmployee(@RequestBody Vehicle vehicle){
        try {
            return new ResponseEntity<>(vehicleRepository.save(vehicle), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/vehicles/{id}")
    ResponseEntity<Optional<Vehicle>> findVehicle (@PathVariable UUID id) {
        try {
            if(!vehicleRepository.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(vehicleRepository.findById(id), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/vehicles/{id}")
    ResponseEntity<Vehicle> replaceVehicle(@RequestBody Vehicle newVehicle,@PathVariable UUID id) {
        try {
            Optional<Vehicle> Vehicle = vehicleRepository.findById(id);
            if(Vehicle.isPresent()){
                Vehicle.get().setVehicleType(newVehicle.getVehicleType());
                Vehicle.get().setDescription(newVehicle.getDescription());
                Vehicle.get().setFuelType(newVehicle.getFuelType());
                Vehicle.get().setHorsePower(newVehicle.getHorsePower());
                Vehicle.get().setMileage(newVehicle.getMileage());
                Vehicle.get().setMileageLimit(newVehicle.getMileageLimit());
                Vehicle.get().setSeats(newVehicle.getSeats());
                Vehicle.get().setGearBoxType(newVehicle.getGearBoxType());
                return new ResponseEntity<>(vehicleRepository.save(Vehicle.get()),HttpStatus.OK);
            }
            else {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/vehicles/{id}")
    ResponseEntity<HttpStatus> deleteVehicle(@PathVariable UUID id) {
        try {
            vehicleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
