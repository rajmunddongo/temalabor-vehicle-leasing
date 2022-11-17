package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Bike;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Car;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class VehicleServiceTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RentalRepository   rentalRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private  Car car;
    private Rental rent;

    @BeforeEach
    void setUp() {
        //Creating entities
        car =Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("ASD-123")
                .seats(5)
                .mileage(1544)
                .mileageLimit(300)
                .horsePower(900)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build();
        Bike bike = Bike.builder()
                .vehicleType(VehicleType.BIKE)
                .licensePlate("ZYZ-143")
                .seats(2)
                .mileage(13242)
                .mileageLimit(300)
                .horsePower(90)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL)
                .strokeType(BikeStrokeType.FOUR_STROKE)
                .build();
        customer = Customer.builder()
                .rentals(new ArrayList<Rental>())
                .rentals(null)
                .bonusPoints(0)
                .firstName("Laci")
                .lastName("Nemeth")
                .drivingLicenceNumber("Laci 22")
                .password("passwd")
                .username("laci22")
                .build();
        rent = Rental.builder()
                .customer(customer)
                .vehicle(car)
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .price(200)
                .useKm(2000)
                .build();
        Rental rent2 = Rental.builder()
                .customer(null)
                .vehicle(bike)
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .price(200)
                .useKm(2000)
                .build();
        ArrayList<Rental> rentals= new ArrayList<>();
        //linking rentals to customer
        customer.setRentals(rentals);

        customerRepository.save(customer);
        rentals.add(rent);
        vehicleRepository.save(
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
                        .build()
        );
        vehicleRepository.save(bike);
        vehicleRepository.save(car);
        customerRepository.save(customer);
        rentalRepository.save(rent);
    }

    @AfterEach
    void tearDown() {
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Test
    void averageSeatsOfVehicleType() {
        assertEquals(1.5, vehicleService.averageSeatsOfVehicleType(VehicleType.BIKE));
        assertEquals(5, vehicleService.averageSeatsOfVehicleType(VehicleType.CAR));
        assertEquals(0, vehicleService.averageSeatsOfVehicleType(VehicleType.TRANSPORTER));
    }

    @Test
    void averageMileageLimitOfVehicleType() {
        assertEquals(250, vehicleService.averageMileageLimitOfVehicleType(VehicleType.BIKE));
        assertEquals(300, vehicleService.averageMileageLimitOfVehicleType(VehicleType.CAR));
        assertEquals(0, vehicleService.averageMileageLimitOfVehicleType(VehicleType.TRANSPORTER));
    }

    @Test
    void allMileageTraveled(){ //TDD
        assertEquals(1544+13242+1234,vehicleService.allMileageTraveled(null));
    }
    @Test
    void mileageTraveledByCustomer(){
        assertEquals(1544,vehicleService.allMileageTraveled(customer));
    }
    @Test
    void rentalRepositoryFindByVehicle(){
        ArrayList<Rental> rents = (ArrayList<Rental>) rentalRepository.findByVehicle(car);
        assertEquals(rent.getId(),rents.get(0).getId());
    }
}