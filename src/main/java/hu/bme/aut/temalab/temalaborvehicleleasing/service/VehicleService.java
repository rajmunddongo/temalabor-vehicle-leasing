package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;

import java.util.List;

public class VehicleService {
    private VehicleRepository vehicleRepository;

    public double calcAverageSeats(String type){
        List<Vehicle> foundVehicles = vehicleRepository.findByType(type);
        int seats=0;
        int counter=0;
        for( Vehicle vehicle : foundVehicles){
            seats+=vehicle.getSeats();
            counter++;
        }
        double calc = seats/counter;
        return calc;
    }
    public double calcMileageLimit(String type){
        List<Vehicle> foundVehicles = vehicleRepository.findByType(type);
        int mileage=0;
        int counter=0;
        for( Vehicle vehicle : foundVehicles){
            mileage+=vehicle.getMileageLimit();
            counter++;
        }
        double calc = mileage/counter;
        return calc;
    }

}
