package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Bike;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.BikeStrokeType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.GearboxType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceMockitoTest {
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private RentalRepository rentalRepository;
	
	@InjectMocks
	private CustomerService customerService;
	
	private List<Customer> customers;
	private Vehicle vehicle;
	private List<Rental> rentals;
	
	@BeforeEach
	void setUp() {
		
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
		
		customers.add(Customer.builder()
						.firstName("Homer")
						.lastName("Simpson")
						.username("homie")
						.password("duffbeer")
						.drivingLicenceNumber("AAAAAAAA")
						.build());
		
		customers.add(Customer.builder()
						.firstName("Marge")
						.lastName("Simpson")
						.username("margiee")
						.password("familylove")
						.drivingLicenceNumber("BBBBBBBB")
						.build());
		
		customers.add(Customer.builder()
						.firstName("Bart")
						.lastName("Simpson")
						.username("skateboardBoy")
						.password("vandalismisfun")
						.drivingLicenceNumber("CCCCCCCC")
						.build());
		
		customers.add(Customer.builder()
						.firstName("Lisa")
						.lastName("Simpson")
						.username("catlady")
						.password("beeinganerdisfun")
						.drivingLicenceNumber("DDDDDDDD")
						.build());
		
		customers.add(Customer.builder()
						.firstName("Maggie")
						.lastName("Simpson")
						.username("babygirl")
						.password("cuppcupp")
						.drivingLicenceNumber("EEEEEEEE")
						.build());
		
		vehicle = Bike.builder()
		                .vehicleType(VehicleType.BIKE)
		                .licensePlate("ABC-123")
		                .seats(1)
		                .mileage(1234)
		                .mileageLimit(200)
		                .horsePower(95)
		                .gearBoxType(GearboxType.MANUAL)
		                .fuelType(FuelType.PETROL_PREMIUM)
		                .strokeType(BikeStrokeType.FOUR_STROKE)
		                .build();
		
		rentals.add(Rental.builder()
						.vehicle(vehicle)
						.customer(customers.get(0))
						.startDate(LocalDate.of(2000, 1, 1))
						.endDate(LocalDate.of(2000, 1, 7))
						.price(500)
						.useKm(1000)
						.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(0))
        				.startDate(LocalDate.now().minusYears(3))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(1))
        				.startDate(LocalDate.now())
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(2))
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(2))
        				.startDate(LocalDate.now().minusYears(3))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(3))
        				.startDate(LocalDate.of(2000, 2, 1))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(3))
        				.startDate(LocalDate.now())
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
		
		rentals.add(Rental.builder()
        				.vehicle(vehicle)
        				.customer(customers.get(3))
        				.startDate(LocalDate.of(2000, 2, 5))
        				.endDate(LocalDate.of(2000, 2, 7))
        				.price(500)
        				.useKm(1000)
        				.build());
	}
	
	@Test
	void findInactiveCustomersTest() {
		
		when(customerRepository.findAll()).thenReturn(customers);
		
		when(rentalRepository.findByCustomer(customers.get(0))).thenReturn(List.of(rentals.get(0), rentals.get(1)));
		when(rentalRepository.findByCustomer(customers.get(1))).thenReturn(List.of(rentals.get(2)));
		when(rentalRepository.findByCustomer(customers.get(2))).thenReturn(List.of(rentals.get(3), rentals.get(4)));
		when(rentalRepository.findByCustomer(customers.get(3))).thenReturn(List.of(rentals.get(5), rentals.get(6), rentals.get(7)));
		when(rentalRepository.findByCustomer(customers.get(4))).thenReturn(List.of());

		Collection<Customer> inactiveCustomers = customerService.findInactiveCustomers();
		
		assertEquals(2, inactiveCustomers.size());
		
		assertEquals(customers.get(0), List.copyOf(inactiveCustomers).get(0));
		assertEquals(customers.get(2), List.copyOf(inactiveCustomers).get(1));
	}
}
