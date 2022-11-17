package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    /**
     * Calculate the number of rentals by vehicles
     *
     * @return Map of Vehicle with number of rentals
     */
    @Transactional(readOnly = true)
    public List<Vehicle> vehiclesOrderedByRentals() {
        // Calculate the number of rentals by vehicles
        Map<Vehicle, Long> vehicleRentalCountMap = rentalRepository.findAll().stream()
                .collect(Collectors.groupingBy(Rental::getVehicle, Collectors.counting()));

        // Order by rentals reversed
        return vehicleRentalCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Calculates the total spend of a customer
     *
     * @param customer Customer data
     * @return total spending
     */
    public Integer totalSpending(@NonNull Customer customer) {
        return rentalRepository.findByCustomer(customer).stream().mapToInt(Rental::getPrice).sum();
    }

    /**
     * Calculates the total kilometers traveled by a customer
     *
     * @param customer Customer data
     * @return total kilometers
     */
    public Integer totalLengthKm(@NonNull Customer customer) {
        return rentalRepository.findByCustomer(customer).stream().mapToInt(Rental::getUseKm).sum();
    }
}
