package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.RentalRelationships;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRelationshipsRepository;

import java.util.*;

public class RentalRelationshipsService {
    private RentalRelationshipsRepository rentalRelationshipsRepository;

    public List<Integer> mostRentalCarsIDList(){
        List<RentalRelationships> allRental = rentalRelationshipsRepository.getAll();
        HashMap<Integer, Integer> carMap = new HashMap<>();
        for (RentalRelationships rent: allRental) {
            int piece = 0;
            if(carMap.get(rent.getVehicleID())!=null){
                piece = carMap.get(rent.getVehicleID());
            }
            carMap.put(rent.getVehicleID(),piece);
        }
        List<Map.Entry<Integer,Integer>> list = new LinkedList<>(carMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        List<Integer> mostRentalCarsID = new ArrayList<>();
        for (Map.Entry<Integer, Integer> map: list) {
            mostRentalCarsID.add(map.getKey());
        }
        return mostRentalCarsID;
    }

    public Integer totalSpending(Integer customerID){
        List<RentalRelationships> foundRental = rentalRelationshipsRepository.findByCustomerID(customerID);
        Integer totalSpend =0;
        for (RentalRelationships rental: foundRental) {
            totalSpend += rental.getPrice();
        }
        return totalSpend;
    }
    public Integer totalLengthKm(Integer customerID){
        List<RentalRelationships> foundRental = rentalRelationshipsRepository.findByCustomerID(customerID);
        Integer totalKM =0;
        for (RentalRelationships rental: foundRental) {
            totalKM += rental.getUseKm();
        }
        return totalKM;
    }
}
