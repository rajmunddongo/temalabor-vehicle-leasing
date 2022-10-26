package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    /**
     * Calculate average seats for a given type
     *
     * @param type {@link VehicleType}
     * @return average
     */
    public double averageSeatsOfVehicleType(@NonNull VehicleType type) {
        return vehicleRepository.findByVehicleType(type).stream()
                .mapToInt(Vehicle::getSeats)
                .average()
                .orElse(0);
    }

    /**
     * Calculate average mileage of vehicle type
     *
     * @param type {@link VehicleType}
     * @return average
     */
    public double averageMileageLimitOfVehicleType(@NonNull VehicleType type) {
        return vehicleRepository.findByVehicleType(type).stream()
                .mapToInt(Vehicle::getMileageLimit)
                .average()
                .orElse(0);
    }

}
