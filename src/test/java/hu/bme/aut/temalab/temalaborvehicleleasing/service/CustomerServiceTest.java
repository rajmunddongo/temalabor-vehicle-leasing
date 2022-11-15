package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Car;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.CarBodyTypes;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.GearboxType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.UserRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        customerRepository.saveAll(List.of(
                        Customer.builder()
                                .firstName("John")
                                .lastName("Smith")
                                .username("jhonny13")
                                .password("UltraSecurePassword5647")
                                .drivingLicenceNumber("125485268KJZT")
                                .bonusPoints(20)
                                .rentals(new ArrayList<>())
                                .build(),


                        Customer.builder()
                                .firstName("Liam")
                                .lastName("Dong")
                                .username("liam25")
                                .password("gishsn")
                                .drivingLicenceNumber("125485268AJRT")
                                .bonusPoints(30)
                                .rentals(new ArrayList<>())
                                .build(),


                        Customer.builder()
                                .firstName("Max")
                                .lastName("Boyer")
                                .username("maxiboy47")
                                .password("jbjkshf552")
                                .drivingLicenceNumber("125485268NBRE")
                                .bonusPoints(40)
                                .rentals(new ArrayList<>())
                                .build(),


                        Customer.builder()
                                .firstName("Homer")
                                .lastName("Simpson")
                                .username("duffbeer")
                                .password("ilovedoughnuts")
                                .drivingLicenceNumber("125485268CXYÍ")
                                .bonusPoints(50)
                                .rentals(new ArrayList<>())
                                .build(),


                        Customer.builder()
                                .firstName("Marge")
                                .lastName("Simpson")
                                .username("meggie")
                                .password("familylove")
                                .drivingLicenceNumber("125485268ASDF")
                                .bonusPoints(60)
                                .rentals(new ArrayList<>())
                                .build()

                )
        );


        vehicleRepository.save(Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("ASD-123")
                .seats(5)
                .mileage(1544)
                .mileageLimit(300)
                .horsePower(900)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build()
        );


//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(0)).startDate(new Date(25485448L)).endDate(new Date(254854458L)).price(2500).useKm(100).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(0)).startDate(new Date(987854958977L)).endDate(new Date(997854958977L)).price(3500).useKm(200).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(0)).startDate(new Date(1200000000000L)).endDate(new Date(1220000000000L)).price(4500).useKm(300).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(0)).startDate(new Date()).endDate(new Date()).price(5500).useKm(400).build());
//
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(1)).startDate(new Date(999959998977L)).endDate(new Date(999999998977L)).price(2600).useKm(500).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(1)).startDate(new Date(1200000000000L)).endDate(new Date(1220000000000L)).price(3600).useKm(600).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(1)).startDate(new Date()).endDate(new Date()).price(4600).useKm(700).build());
//
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(2)).startDate(new Date(1200000000000L)).endDate(new Date(1220000000000L)).price(5600).useKm(800).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(2)).startDate(new Date()).endDate(new Date()).price(2700).useKm(900).build());
//
//        //Inactive customer
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(3)).startDate(new Date(354854958977L)).endDate(new Date(364854958977L)).price(3700).useKm(1000).build());
//
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(4)).startDate(new Date(1200000000000L)).endDate(new Date(1220000000000L)).price(4700).useKm(1100).build());
//        rentals.add(Rental.builder().vehicle(vehicle).customer(customers.get(4)).startDate(new Date()).endDate(new Date()).price(5700).useKm(1200).build());
//
//        customers.get(0).setRentals(rentals.subList(0, 4));
//        customers.get(1).setRentals(rentals.subList(4, 7));
//        customers.get(2).setRentals(rentals.subList(7, 9));
//        customers.get(3).setRentals(rentals.subList(9, 10));
//        customers.get(4).setRentals(Arrays.asList(rentals.get(10), rentals.get(11)));
//
//        //Save all rentals to database
//        for (Rental r : rentals) {
//            rentalRepository.save(r);
//        }

    }

    @AfterEach
    void tearDown() {
        rentalRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findMostActiveCustomersTest() {

    }

    @Test
    void giveOutBonusPointsToMostActiveCustomersTest() {

    }

    @Test
    void findInactiveCustomersTest() {
        Collection<Customer> inactiveCustomers = customerService.findInactiveCustomers();

        assertEquals(1, inactiveCustomers.size());
        assertEquals("Homer", inactiveCustomers.iterator().next().getFirstName());
    }
}
