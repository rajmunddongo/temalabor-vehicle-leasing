package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Collection<Vehicle> findByVehicleType(@NonNull VehicleType vehicleType);
    Optional<Vehicle> findById(UUID id);
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
