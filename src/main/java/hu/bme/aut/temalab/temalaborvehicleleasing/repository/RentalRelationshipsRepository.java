package hu.bme.aut.temalab.temalaborvehicleleasing.repository;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.RentalRelationships;

import java.util.List;

public interface RentalRelationshipsRepository {
        List<RentalRelationships> getAll();
        List<RentalRelationships> findByVehicleID(Integer vehicleID);
        List<RentalRelationships> findByCustomerID(Integer customerID);
        boolean save (RentalRelationships rentalRelationships);
}
