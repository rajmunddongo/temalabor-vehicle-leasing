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
public final class VehicleService<vehicles> {
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
     * Returns all the mileage traveled, if theres a customer given, gives back the mileage traveled by the customer
     * @param customer optional param
     * @return
     */
    public int allMileageTraveled(Customer customer){
        int allMileageTraveled = 0;
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) vehicleRepository.findAll();

        if(customer==null){
            for(Vehicle vehicle :vehicles){
                allMileageTraveled += vehicle.getMileage();
            }
            return allMileageTraveled;
        }
        ArrayList<Rental> rentals =(ArrayList<Rental>) rentalRepository.findByCustomer(customer);
        for(Vehicle vehicle :vehicles){
            for(Rental rental : rentals) {
                if(rental.getVehicle().equals(vehicle))
                    allMileageTraveled += vehicle.getMileage();
            }
        }
        return allMileageTraveled;
    }

}
