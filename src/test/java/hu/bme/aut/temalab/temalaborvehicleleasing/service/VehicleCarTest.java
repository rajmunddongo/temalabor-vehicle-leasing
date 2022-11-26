package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class VehicleCarTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private RentalRepository rentalRepository;
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService=new VehicleService(vehicleRepository,rentalRepository);
    }
    @AfterEach
    void tearDown() {
        vehicleRepository.deleteAll();
    }

    @Test
    void testMethodCall() {
        vehicleService.averageSeatsOfVehicleType(VehicleType.CAR);
        verify(vehicleRepository,Mockito.times(1)).findByVehicleType(VehicleType.CAR);
    }
    @Test
    void testThrow() {
        try {
                Mockito.when(vehicleRepository.findByVehicleType(VehicleType.valueOf("THROWME")))
                        .thenThrow(IllegalArgumentException.class);
        }catch(IllegalArgumentException e ){
            LogManager.getLogger().info("IllegalArgumentException catched");
        }
    }

}
