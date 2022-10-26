package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public final class RentalRelationshipsService {
    private final RentalRepository rentalRelationshipsRepository;

    public List<Vehicle> mostRentalCarsIDList() {
        List<Rental> allRental = rentalRelationshipsRepository.findAll();
        HashMap<Vehicle, Integer> carMap = new HashMap<>();
        for (Rental rent : allRental) {
            int piece = 0;
            if (carMap.get(rent.getVehicle()) != null) {
                piece = carMap.get(rent.getVehicle());
            }
            carMap.put(rent.getVehicle(), piece);
        }
        List<Map.Entry<Vehicle, Integer>> list = new LinkedList<>(carMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        List<Vehicle> mostRentalCarsID = new ArrayList<>();
        for (Map.Entry<Vehicle, Integer> map : list) {
            mostRentalCarsID.add(map.getKey());
        }
        return mostRentalCarsID;
    }

    public Integer totalSpending(Customer customer) {
        Collection<Rental> foundRental = rentalRelationshipsRepository.findByCustomer(customer);
        Integer totalSpend = 0;
        for (Rental rental : foundRental) {
            totalSpend += rental.getPrice();
        }
        return totalSpend;
    }

    public Integer totalLengthKm(Customer customer) {
        Collection<Rental> foundRental = rentalRelationshipsRepository.findByCustomer(customer);
        Integer totalKM = 0;
        for (Rental rental : foundRental) {
            totalKM += rental.getUseKm();
        }
        return totalKM;
    }
}
