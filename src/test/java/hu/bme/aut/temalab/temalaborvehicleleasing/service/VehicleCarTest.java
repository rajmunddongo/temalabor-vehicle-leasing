package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Bike;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Car;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.BikeStrokeType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.GearboxType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleCarTest {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;
    @BeforeEach
    void setUp() {
        vehicleRepository.save(
                Car.builder()
                        .vehicleType(VehicleType.CAR)
                        .licensePlate("ABC-123")
                        .seats(1)
                        .mileage(1234)
                        .mileageLimit(200)
                        .horsePower(95)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL_PREMIUM)
                        .build()
        );
}
