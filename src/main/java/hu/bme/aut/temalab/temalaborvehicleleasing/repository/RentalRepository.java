package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    /**
     * Finds a rentals by a vehicle in the database
     *
     * @param vehicle by searching the rental
     * @return an empty or a filled Collection<Rental> according to the process's result
     */
    Collection<Rental> findByVehicle(@NonNull Vehicle vehicle);
    /**
     * Finds a rentals by a customer in the database
     *
     * @param customer by searching the rentals
     * @return an empty or a filled Collection<Rental> according to the process's result
     */
    Collection<Rental> findByCustomer(@NonNull Customer customer);
}
