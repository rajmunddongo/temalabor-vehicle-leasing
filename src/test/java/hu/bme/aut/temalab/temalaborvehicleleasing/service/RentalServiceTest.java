package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class RentalServiceTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalService rentalService;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private UUID customer1;
    private UUID customer2;
    private UUID customer3;
    private UUID car;
    private UUID bike;

    private UUID rental1;
    private UUID rental2;
    private UUID rental3;

    @BeforeEach
    @Transactional
    void setUp() {
        customer1 = customerRepository.save(Customer.builder()
                .firstName("Bob")
                .lastName("Bob")
                .username("bob")
                .password("bob")
                .build()).getId();

        customer2 = customerRepository.save(Customer.builder()
                .firstName("Alice")
                .lastName("Alice")
                .username("alice")
                .password("alice")
                .build()).getId();

        customer3 = customerRepository.save(Customer.builder()
                .firstName("Evil")
                .lastName("Evil")
                .username("evil")
                .password("evil")
                .build()).getId();

        bike = vehicleRepository.save(
                Bike.builder()
                        .vehicleType(VehicleType.BIKE)
                        .licensePlate("ABC-123")
                        .seats(1)
                        .mileage(1234)
                        .mileageLimit(200)
                        .horsePower(95)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL_PREMIUM)
                        .strokeType(BikeStrokeType.FOUR_STROKE)
                        .build()).getId();

        car = vehicleRepository.save(Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("ASD-354")
                .seats(5)
                .mileage(1544)
                .mileageLimit(300)
                .horsePower(900)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build()).getId();

        rental1 = rentalRepository.save(Rental.builder()
                .price(1000)
                .startDate(LocalDate.of(2022, Calendar.OCTOBER, 10))
                .endDate(LocalDate.of(2022, Calendar.OCTOBER, 25))
                .useKm(50)
                .customer(customerRepository.findById(customer1).get())
                .vehicle(vehicleRepository.findById(bike).get())
                .build()).getId();

        rental2 = rentalRepository.save(Rental.builder()
                .price(2000)
                .startDate(LocalDate.of(2022, Calendar.SEPTEMBER, 10))
                .endDate(LocalDate.of(2022, Calendar.OCTOBER, 8))
                .useKm(200)
                .customer(customerRepository.findById(customer2).get())
                .vehicle(vehicleRepository.findById(car).get())
                .build()).getId();

        rental3 = rentalRepository.save(Rental.builder()
                .price(1000)
                .startDate(LocalDate.of(2022, Calendar.AUGUST, 10))
                .endDate(LocalDate.of(2022, Calendar.AUGUST, 25))
                .useKm(5)
                .customer(customerRepository.findById(customer2).get())
                .vehicle(vehicleRepository.findById(car).get())
                .build()).getId();
    }

    @AfterEach
    void tearDown() {
        rentalRepository.deleteAll();
        vehicleRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void mostRentalCarsIDList() {
        List<Vehicle> mostRentalCars = rentalService.vehiclesOrderedByRentals();
        Iterator<Vehicle> iterator = mostRentalCars.iterator();
        assertEquals(car, iterator.next().getId());
        assertEquals(bike, iterator.next().getId());
        assertEquals(2, mostRentalCars.size());
    }

    @Test
    void totalSpending() {
        assertEquals(1000, rentalService.totalSpending(customerRepository.findById(customer1).get()));
        assertEquals(3000, rentalService.totalSpending(customerRepository.findById(customer2).get()));
        assertEquals(0, rentalService.totalSpending(customerRepository.findById(customer3).get()));
    }

    @Test
    void totalLengthKm() {
        assertEquals(50, rentalService.totalLengthKm(customerRepository.findById(customer1).get()));
        assertEquals(205, rentalService.totalLengthKm(customerRepository.findById(customer2).get()));
        assertEquals(0, rentalService.totalLengthKm(customerRepository.findById(customer3).get()));
    }
    @Test
    void totalSpendingAfterDateTest(){
        assertEquals(2000, rentalService.totalSpendingAfterDate(customerRepository.findById(customer2).get(), LocalDate.of(2022, Calendar.SEPTEMBER, 8)));
        assertEquals(0, rentalService.totalSpendingAfterDate(customerRepository.findById(customer2).get(), LocalDate.of(2022, Calendar.DECEMBER, 8)));
    }
}