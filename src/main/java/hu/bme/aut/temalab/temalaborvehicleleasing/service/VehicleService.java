package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

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

    /**
     * Filter vehicles by their type of fuel
     * @param fuelType
     * @return filtered list
     */
    public List<Vehicle> filterVehicleByFuelType(FuelType fuelType){
        return vehicleRepository.findByFuelType(fuelType).stream().distinct().toList();
    }

    /**
     * Filter vehicles by their type and their fuel type in the same time
     * @param fuelType
     * @param vehicleType
     * @return filtered list
     */
    public List<Vehicle> filterVehicleByFuelAndVehicleType(FuelType fuelType,VehicleType vehicleType){
        ArrayList<Vehicle> filteredvehicles = new ArrayList<>();
        for(Vehicle v1 : new ArrayList<>(vehicleRepository.findByFuelType(fuelType).stream().distinct().toList())){
            for(Vehicle v2 :  new ArrayList<>(vehicleRepository.findByVehicleType(vehicleType).stream().distinct().toList())){
                if(v1.getId().equals(v2.getId())) filteredvehicles.add(v1);
            }
        }
        return filteredvehicles;
    }

    /**
     * Sort vehicles by their mile limit.
     * @return sortedList
     */
    public List<Vehicle>  vehiclesSortedByMileLimit(){
        return vehicleRepository.findAll().stream().distinct().sorted(new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return -o1.getMileageLimit()+o2.getMileageLimit();
            }
        }).toList();
    }

    /**
     * Returns supercars. A supercar means a car with horsepower in the top 30% and 2 or less seats.
     * @return filtered list
     */
    public List<Vehicle> superCars(){
        double  size= vehicleRepository.findAll().stream().toList().size();
        double limitofhorsepower = vehicleRepository.findAll().stream().sorted(new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return -o1.getHorsePower()+o2.getHorsePower();
            }
        }).toList().get((int)Math.floor((size*0.3))).getHorsePower();
        return vehicleRepository.findAll().stream().filter(new Predicate<Vehicle>() {
            @Override
            public boolean test(Vehicle vehicle) {
                if(vehicle.getSeats()<=2 && vehicle.getHorsePower()>=limitofhorsepower) return true;
                return false;
            }
        }).toList();
    }

}