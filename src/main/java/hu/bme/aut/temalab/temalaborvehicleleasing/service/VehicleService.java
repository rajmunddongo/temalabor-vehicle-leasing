package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public final class VehicleService{
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;



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

    /**
     * Calculate all mileage traveled by all vehicles
     * @return all mileage traveled by all vehicles
     */
    public int allMileageTraveled(){
        int allMileageTraveled = 0;
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) vehicleRepository.findAll();
            for(Vehicle vehicle :vehicles){
                allMileageTraveled += vehicle.getMileage();
            }
        return allMileageTraveled;
    }

    /**
     * Calculate all mileage traveled by the given customer
     * @param customer
     * @return mileage traveled by the given customer
     */
    public int allMileageTraveledByCustomer(@NonNull Customer customer){
        int allMileageTraveled = 0;
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) vehicleRepository.findAll();
        ArrayList<Rental> rentals =(ArrayList<Rental>) rentalRepository.findByCustomer(customer);
        for(Vehicle vehicle :vehicles){
            for(Rental rental : rentals) {
                if(rental.getVehicle().getId().equals(vehicle.getId()))
                    allMileageTraveled += vehicle.getMileage();
            }
        }
        return allMileageTraveled;
    }
}