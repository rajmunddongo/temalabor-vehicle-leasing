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
    Collection<Rental> findByVehicle(@NonNull Vehicle vehicle);

    Collection<Rental> findByCustomer(@NonNull Customer customer);

    Optional<Rental> findByid(UUID id);
}
