package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
	Optional<Administrator> findByEmployeeNumber(String employeeNumber);
}
