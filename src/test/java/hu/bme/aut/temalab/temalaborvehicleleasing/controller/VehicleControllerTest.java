package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Car;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Vehicle;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.CarBodyTypes;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.GearboxType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleRepository vehicleRepository;

    private List<Vehicle> vehicleList;

    @BeforeEach
    void SetUp(){
        
    	vehicleList= new ArrayList<>();
        
    	vehicleList.add(Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("ASD-123")
                .seats(5)
                .id(UUID.fromString("a6a8669e-ee95-4c42-9ef6-4a9b61380164"))
                .mileage(1544)
                .mileageLimit(300)
                .horsePower(900)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build());
    	
        vehicleList.add(Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("BUGatti")
                .id(UUID.fromString("e6a8669e-ee95-4c42-9ef6-4a9b61380164"))
                .seats(2)
                .mileage(1044)
                .mileageLimit(30)
                .horsePower(1500)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build());
        
        vehicleList.add(Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("ABB-133")
                .seats(2)
                .id(UUID.fromString("b6a8669e-ee95-4c42-9ef6-4a9b61380164"))
                .mileage(1444)
                .mileageLimit(100)
                .horsePower(800)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build());
    }

    @Test
    public void findall() throws Exception {
       
    	when(vehicleRepository.findAll()).thenReturn(vehicleList);

        mockMvc.perform(get("/vehicles/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(vehicleList.size()));
    }
    @Test
    public void createVehicleTest() throws  Exception{
       
    	when(vehicleRepository.save(any(Vehicle.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        
    	Vehicle newVehicle = Car.builder()
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
    	
        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(post("/vehicles/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newVehicle)))
                .andExpect(status().isOk());
    }
    @Test
    public void findVehicleByIdTest() throws Exception{
        
    	when(vehicleRepository.findById(UUID.fromString("e6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.of(vehicleList.get(1)));
        
    	mockMvc.perform(get("/vehicles/e6a8669e-ee95-4c42-9ef6-4a9b61380164"))
                .andExpect(status().isOk());
    	
        when(vehicleRepository.findById(UUID.fromString("b6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/vehicles/b6a8669e-ee95-4c42-9ef6-4a9b6128016")).andExpect(status().isNotFound());

    }
    @Test
    public void updateVehicleTest() throws Exception{
       
    	Vehicle newVehicle = Car.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("FFF-123")
                .seats(3)
                .mileage(1344)
                .mileageLimit(100)
                .horsePower(200)
                .gearBoxType(GearboxType.MANUAL)
                .fuelType(FuelType.PETROL_PREMIUM)
                .carBodyTypes(CarBodyTypes.COUPE)
                .build();

        when(vehicleRepository.findById(UUID.fromString("a6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.of(vehicleList.get(0)));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(newVehicle);

        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(post("/vehicles/a6a8669e-ee95-4c42-9ef6-4a9b61380164/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newVehicle)))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteVehicleTest() throws Exception{
        
    	doNothing().when(vehicleRepository).deleteById(any());
        
    	mockMvc.perform(delete("/vehicles/a6a8669e-ee95-4c42-9ef6-4a9b61380164/delete")).andExpect(status().isNoContent());
    }
}
