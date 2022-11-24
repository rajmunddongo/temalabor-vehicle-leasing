package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Bike;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Car;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.BikeStrokeType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.CarBodyTypes;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.GearboxType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.UserRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private RentalRepository rentalRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@BeforeEach
	void setUp() {
		//Create 5 users
		userRepository.save(
				Customer.builder()
						.firstName("Homer")
						.lastName("Simpson")
						.username("homie")
						.password("duffbeer")
						.drivingLicenceNumber("AAAAAAAA")
						.bonusPoints(5)
						.build()
		);
		
		userRepository.save(
				Customer.builder()
						.firstName("Marge")
						.lastName("Simpson")
						.username("margiee")
						.password("familylove")
						.drivingLicenceNumber("BBBBBBBB")
						.bonusPoints(5)
						.build()
		);
		
		userRepository.save(
				Customer.builder()
						.firstName("Bart")
						.lastName("Simpson")
						.username("skateboardBoy")
						.password("vandalismisfun")
						.drivingLicenceNumber("CCCCCCCC")
						.bonusPoints(5)
						.build()
		);
		
		userRepository.save(
				Customer.builder()
						.firstName("Lisa")
						.lastName("Simpson")
						.username("catlady")
						.password("beeinganerdisfun")
						.drivingLicenceNumber("DDDDDDDD")
						.bonusPoints(5)
						.build()
		);
		
		userRepository.save(
				Customer.builder()
						.firstName("Maggie")
						.lastName("Simpson")
						.username("babygirl")
						.password("cuppcupp")
						.drivingLicenceNumber("EEEEEEEE")
						.bonusPoints(5)
						.build()
		);
		
		//Create 8 vehicles
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

        vehicleRepository.save(
                Bike.builder()
                        .vehicleType(VehicleType.BIKE)
                        .licensePlate("ZYZ-143")
                        .seats(2)
                        .mileage(13242)
                        .mileageLimit(300)
                        .horsePower(90)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL)
                        .strokeType(BikeStrokeType.FOUR_STROKE)
                        .build()
        );

        vehicleRepository.save(
                Car.builder()
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
        
        vehicleRepository.save(
                Car.builder()
                        .vehicleType(VehicleType.CAR)
                        .licensePlate("XXX-111")
                        .seats(5)
                        .mileage(1544)
                        .mileageLimit(300)
                        .horsePower(900)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL_PREMIUM)
                        .carBodyTypes(CarBodyTypes.COUPE)
                        .build()
        );
        
        vehicleRepository.save(
                Bike.builder()
                        .vehicleType(VehicleType.BIKE)
                        .licensePlate("HHH-444")
                        .seats(2)
                        .mileage(13242)
                        .mileageLimit(300)
                        .horsePower(90)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL)
                        .strokeType(BikeStrokeType.FOUR_STROKE)
                        .build()
        );
        
        vehicleRepository.save(
                Bike.builder()
                        .vehicleType(VehicleType.BIKE)
                        .licensePlate("BBB-888")
                        .seats(2)
                        .mileage(13242)
                        .mileageLimit(300)
                        .horsePower(90)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL)
                        .strokeType(BikeStrokeType.FOUR_STROKE)
                        .build()
        );
        
        vehicleRepository.save(
                Car.builder()
                        .vehicleType(VehicleType.CAR)
                        .licensePlate("QQQ-000")
                        .seats(5)
                        .mileage(1544)
                        .mileageLimit(300)
                        .horsePower(900)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL_PREMIUM)
                        .carBodyTypes(CarBodyTypes.COUPE)
                        .build()
        );
        
        vehicleRepository.save(
                Car.builder()
                        .vehicleType(VehicleType.CAR)
                        .licensePlate("PPP-303")
                        .seats(5)
                        .mileage(1544)
                        .mileageLimit(300)
                        .horsePower(900)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL_PREMIUM)
                        .carBodyTypes(CarBodyTypes.COUPE)
                        .build()
        );
        
        //Create 8 rentals
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("ABC-123").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("AAAAAAAA").get())
        				.startDate(LocalDate.of(2000, 1, 1))
        				.endDate(LocalDate.of(2000, 1, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("ZYZ-143").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("AAAAAAAA").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("ASD-123").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("BBBBBBBB").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("XXX-111").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("CCCCCCCC").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("HHH-444").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("CCCCCCCC").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("BBB-888").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("DDDDDDDD").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("QQQ-000").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("DDDDDDDD").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
        rentalRepository.save(
        		Rental.builder()
        				.vehicle(vehicleRepository.findByLicensePlate("PPP-303").get())
        				.customer(customerRepository.findByDrivingLicenceNumber("DDDDDDDD").get())
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build()
        );
        
	}
	
	@AfterEach
	void tearDown() {
		rentalRepository.deleteAll();
		userRepository.deleteAll();
		vehicleRepository.deleteAll();
	}
	
	
	@Test
	void findMostActiveCustomersTest() {		
		
		//Invalid parameter test
		assertEquals(null, customerService.findMostActiveCustomers(-1));
		assertEquals(null, customerService.findMostActiveCustomers(6));
		
		List<Customer> mostActiveCustomers = List.copyOf(customerService.findMostActiveCustomers(1));
		
		assertEquals(1, mostActiveCustomers.size());
		assertEquals(3, rentalRepository.findByCustomer(mostActiveCustomers.get(0)).size());
		
		mostActiveCustomers = List.copyOf(customerService.findMostActiveCustomers(2));
		
		assertEquals(3, mostActiveCustomers.size());
		assertEquals(3, rentalRepository.findByCustomer(mostActiveCustomers.get(0)).size());
		assertEquals(2, rentalRepository.findByCustomer(mostActiveCustomers.get(1)).size());
		assertEquals(2, rentalRepository.findByCustomer(mostActiveCustomers.get(2)).size());
	}
	
	@Test
	void giveOutBonusPointsTest() {
		
		List<Customer> activeCustomers;
		
		customerService.giveOutBonusPoints(15, 1);
		activeCustomers = List.copyOf(customerService.findMostActiveCustomers(1));
		assertEquals(1, activeCustomers.size());
		assertEquals(20, activeCustomers.get(0).getBonusPoints());
		
		customerService.giveOutBonusPoints(Integer.MAX_VALUE, 2);
		activeCustomers = List.copyOf(customerService.findMostActiveCustomers(2));
		assertEquals(3, activeCustomers.size());
		assertEquals(Integer.MAX_VALUE, activeCustomers.get(0).getBonusPoints());
		assertEquals(Integer.MAX_VALUE, activeCustomers.get(1).getBonusPoints());
		assertEquals(Integer.MAX_VALUE, activeCustomers.get(2).getBonusPoints());
	}
	
}
