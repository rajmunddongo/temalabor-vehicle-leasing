package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RentalServiceMockitoTest {
    @Mock
    RentalRepository rentalRepository;
    @InjectMocks
    RentalService rentalService;
    @Test
    void totalSpending() {
        Customer customer = new Customer();
        rentalService.totalSpending(customer);
        verify(rentalRepository, Mockito.times(1)).findByCustomer(customer);
    }

    @Test
    void vehiclesOrderedByRentalsTest() {
        List<Vehicle> mostRentalList = rentalService.vehiclesOrderedByRentals();
        verify(rentalRepository, Mockito.times(1)).findAll();
        assertEquals(0, mostRentalList.size());
        //Add a rental to list
        Rental rental = Rental.builder()
                .customer(new Customer())
                .vehicle(new Bike())
                .build();
        List<Rental> list = new ArrayList<>();
        list.add(rental);
        when(rentalRepository.findAll()).thenReturn(list);
        List<Vehicle> mostRentalList2 = rentalService.vehiclesOrderedByRentals();
        assertEquals(1, mostRentalList2.size());
        Car car = new Car();
        //Add a rental to list
        rental = Rental.builder()
                .customer(new Customer())
                .vehicle(car)
                .build();
        list.add(rental);
        mostRentalList2 = rentalService.vehiclesOrderedByRentals();
        assertEquals(2, mostRentalList2.size());
        //Add a rental but the same Car
        rental = Rental.builder()
                .customer(new Customer())
                .vehicle(car)
                .build();
        list.add(rental);
        mostRentalList2 = rentalService.vehiclesOrderedByRentals();
        assertEquals(2, mostRentalList2.size());
        assertEquals(car, mostRentalList2.get(0));
    }
}
