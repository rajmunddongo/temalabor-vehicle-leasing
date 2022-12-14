package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    
	/**
	 * Finds all rentals in the database containing the given vehicle 
	 * 
	 * @param vehicle a vehicle that a rental should contain
	 * @return a collection of rentals which contain the given vehicle
	 */
	Collection<Rental> findByVehicle(@NonNull Vehicle vehicle);

    /**
     * Finds all rentals in the database containing the given customer
     * 
     * @param customer a customer that a rental should contain
     * @return a collection of rentals which contain the given vehicle
     */
	Collection<Rental> findByCustomer(@NonNull Customer customer);

    /**
     * Finds a rental in the database which has the given ID
     * 
     * @param id the desired ID that a rental should have
     * @return an empty or a filled Optional (containing the found rental) according to the process's result
     */
	Optional<Rental> findByid(UUID id);
}
