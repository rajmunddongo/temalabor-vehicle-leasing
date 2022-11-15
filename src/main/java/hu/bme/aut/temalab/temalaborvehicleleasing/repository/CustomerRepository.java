package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	Optional<Customer> findByDrivingLicenceNumber(@NonNull String drivingLicence);
}
