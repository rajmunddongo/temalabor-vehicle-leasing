package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
	/**
	 * Finds a user in the database
	 * 
	 * @param username identifies the desired user
	 * @return an empty or a filled Optional according to the process's result
	 */
	Optional<User> findByUsername(String username);
}
