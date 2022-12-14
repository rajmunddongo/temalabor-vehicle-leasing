package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
	
	/**
	 * Finds an administrator in the database
	 * 
	 * @param employeeNumber identifies the desired administrator
	 * @return an empty or a filled Optional (containing the found administrator) according to the process's result
	 */
	Optional<Administrator> findByEmployeeNumber(String employeeNumber);
}
