package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Bike;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Car;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class VehicleServiceTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
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
    }

    @AfterEach
    void tearDown() {
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
}