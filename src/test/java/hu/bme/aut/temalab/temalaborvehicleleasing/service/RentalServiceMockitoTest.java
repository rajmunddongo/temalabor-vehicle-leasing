package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RentalServiceMockitoTest {
    @Mock
    RentalRepository rentalRepository;
    @InjectMocks
    RentalRelationshipsService rentalRelationshipsService;
    @BeforeEach
    void setUp() {
        rentalRelationshipsService = new RentalRelationshipsService(rentalRepository);
    }
    @AfterEach
    void tearDown() {
        rentalRepository.deleteAll();
    }
    @Test
    void totalSpending() {
        Customer customer = new Customer();
        rentalRelationshipsService.totalSpending(customer);
        verify(rentalRepository, Mockito.times(1)).findByCustomer(customer);
    }

    @Test
    void mostRentalCarsIDListTest() {
        List<Vehicle> mostRentalList = rentalRelationshipsService.mostRentalCarsIDList();
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
        List<Vehicle> mostRentalList2 = rentalRelationshipsService.mostRentalCarsIDList();
        assertEquals(1, mostRentalList2.size());
        Car car = new Car();
        //Add a another rental to list
        rental = Rental.builder()
                .customer(new Customer())
                .vehicle(car)
                .build();
        list.add(rental);
        mostRentalList2 = rentalRelationshipsService.mostRentalCarsIDList();
        assertEquals(2, mostRentalList2.size());
        //Add a another rental but the same Car
        rental = Rental.builder()
                .customer(new Customer())
                .vehicle(car)
                .build();
        list.add(rental);
        mostRentalList2 = rentalRelationshipsService.mostRentalCarsIDList();
        assertEquals(2, mostRentalList2.size());
        assertEquals(car, mostRentalList2.get(0));
    }
}