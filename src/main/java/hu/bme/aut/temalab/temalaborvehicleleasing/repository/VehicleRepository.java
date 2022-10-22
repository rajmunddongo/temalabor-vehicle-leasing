package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    List<Vehicle> findById(Integer id);
    List<Vehicle> findByType(String type);
    boolean save (Vehicle vehicle);
}
