package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public final class RentalRelationshipsService {
    private final RentalRepository rentalRelationshipsRepository;

    /**
     * Calculate most rental Cars
     *
     * @return organized list of cars
     */
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

    /**
     *Calculates the total spend of a customer
     *
     * @param customer Customer data
     * @return total spending
     */
    public Integer totalSpending(@NonNull Customer customer) {
        Collection<Rental> foundRental = rentalRelationshipsRepository.findByCustomer(customer);
        Integer totalSpend = 0;
        for (Rental rental : foundRental) {
            totalSpend += rental.getPrice();
        }
        return totalSpend;
    }

    /**
     * Calculates the total kilometers traveled by a customer
     * @param customer Customer data
     * @return total kilometers
     */
    public Integer totalLengthKm(@NonNull Customer customer) {
        Collection<Rental> foundRental = rentalRelationshipsRepository.findByCustomer(customer);
        Integer totalKM = 0;
        for (Rental rental : foundRental) {
            totalKM += rental.getUseKm();
        }
        return totalKM;
    }
}
