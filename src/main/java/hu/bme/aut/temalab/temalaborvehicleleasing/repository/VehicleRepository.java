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
	 * @param vehicleType determines the type of the desired vehicles
	 * @return a collection of vehicles with the given type
	 */
	Collection<Vehicle> findByVehicleType(@NonNull VehicleType vehicleType);
    
	/**
	 * Finds a vehicle in the database with the given ID
	 * 
	 * @param id determines the ID of the desired vehicle
	 * @return an empty or a filled Optional (containing the found vehicle) according to the process's result
	 */
	Optional<Vehicle> findById(UUID id);
    
	/**
	 * Finds a vehicle in the database with the given license plate number
	 * 
	 * @param licensePlate determines the license plate number of the desired vehicle 
	 * @return an empty or a filled Optional (containing the found vehicle) according to the process's result
	 */
	Optional<Vehicle> findByLicensePlate(String licensePlate);

	/**
	 * Finds all vehicles in the database by fuel type
	 * 
	 * @param fuelType
	 * @return filtered vehicles
	 */
    Collection<Vehicle> findByFuelType(@NonNull FuelType fuelType);

}
