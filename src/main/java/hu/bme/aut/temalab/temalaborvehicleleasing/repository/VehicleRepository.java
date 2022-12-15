package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    
	/**
	 * Finds all vehicles in the database with a given type
	 * 
	 * @param vehicleType the desired type of the vehicles
	 * @return a collection of the found vehicles which have the same type
	 */
	Collection<Vehicle> findByVehicleType(@NonNull VehicleType vehicleType);
    
	/**
	 * Finds all vehicles in the database with a given fuel type
	 * 
	 * @param fuelType the desired type of the fuel
	 * @return a collection of the found vehicles which have the same fuel type
	 */
	Collection<Vehicle> findByFuelType(@NonNull FuelType fuelType);
    
	/**
	 * Finds a vehicle in the database with a given ID
	 * 
	 * @param id the desired ID
	 * @return an empty or a filled Optional (containing the found vehicle) according to the process's result
	 */
	Optional<Vehicle> findById(UUID id);
    
	/**
	 * Finds a vehicle in the database with a given license plate number
	 * 
	 * @param licensePlate the desired license plate number
	 * @return an empty or a filled Optional (containing the found vehicle) according to the process's result
	 */
	Optional<Vehicle> findByLicensePlate(String licensePlate);
}
