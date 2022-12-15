package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	
	/**
	 * Finds a customer in the database
	 * 
	 * @param drivingLicence identifies the desired customer
	 * @return an empty or a filled Optional (containing the found customer) according to the process's result
	 */
	Optional<Customer> findByDrivingLicenceNumber(@NonNull String drivingLicence);
}
