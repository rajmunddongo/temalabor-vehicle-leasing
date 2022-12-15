package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

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
    
    /**
     * Gives back all the vehicles present in the database
     * 
     * @return ResponseEntity containing the result list of vehicles (if the process was successful) and a HTTP status code
     */
    @GetMapping("/vehicles/all")
    ResponseEntity<List<Vehicle>> all(){
        
    	try{
            return new ResponseEntity<>(vehicleRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Creates a new vehicle and saves it in the database
     * 
     * @param vehicle the new vehicle that should be saved
     * @return ResponseEntity containing the freshly added vehicle (if the process was successful) and a HTTP status code
     */
    @PostMapping("/vehicles/save")
    ResponseEntity<Vehicle> newVehicle(@RequestBody Vehicle vehicle){
        
    	try {
            return new ResponseEntity<>(vehicleRepository.save(vehicle), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Tries to find a vehicle in the database based on the given ID
     * 
     * @param id the ID that can identify the desired vehicle
     * @return ResponseEntity containing the found vehicle (if it was found) and a HTTP status code
     */
    @GetMapping("/vehicles/{id}")
    ResponseEntity<Optional<Vehicle>> findVehicle (@PathVariable UUID id) {
        
    	try {
            if(!vehicleRepository.findById(id).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(vehicleRepository.findById(id), HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Updates an already present vehicle's data
     * 
     * @param newVehicle Vehicle object that contains the new data
     * @param id the ID of the vehicle whose data needs to be changed
     * @return ResponseEntity containing the updated vehicle (if the process was successful) and a HTTP status code
     */
    @PostMapping("/vehicles/{id}/update")
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
    
    /**
     * Removes a vehicle from the database
     * 
     * @param id identifies the customer that have to be deleted
     * @return ResponseEntity containing a HTTP status code indicating the process's result
     */
    @DeleteMapping("/vehicles/{id}/delete")
    ResponseEntity<HttpStatus> deleteVehicle(@PathVariable UUID id) {
        
    	try {
            vehicleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
