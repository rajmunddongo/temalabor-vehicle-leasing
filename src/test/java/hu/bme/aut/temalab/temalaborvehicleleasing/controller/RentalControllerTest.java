package hu.bme.aut.temalab.temalaborvehicleleasing.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Bike;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.*;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalRepository rentalRepository;

    private List<Rental> rentals;

    @BeforeEach
    void setUp() {
        rentals = new ArrayList<>();
        rentals.add(Rental.builder()
                .price(1000)
                .startDate(LocalDate.of(2022, Calendar.OCTOBER, 10))
                .endDate(LocalDate.of(2022, Calendar.OCTOBER, 25))
                .useKm(50)
                .customer(new Customer())
                .vehicle(new Bike())
                .build());
        rentals.add(Rental.builder()
                .price(1000)
                .startDate(LocalDate.of(2021, Calendar.OCTOBER, 10))
                .endDate(LocalDate.of(2020, Calendar.OCTOBER, 25))
                .useKm(10)
                .customer(new Customer())
                .vehicle(new Bike())
                .build());
        rentals.add(Rental.builder()
                .price(1000)
                .startDate(LocalDate.of(2000, Calendar.OCTOBER, 10))
                .endDate(LocalDate.of(2001, Calendar.OCTOBER, 23))
                .useKm(20)
                .customer(new Customer())
                .vehicle(new Bike())
                .build());
    }

    @Test
    public void findAllTest() throws Exception {
        when(rentalRepository.findAll()).thenReturn(rentals);
        mockMvc.perform(get("/rentals")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(rentals.size()));
    }



    @Test
    public void findCustomerById() throws Exception {
        when(rentalRepository.findById(UUID.fromString("a1a8629e-ee35-4c42-9ef6-4a2b61381164"))).thenReturn(Optional.of(rentals.get(1)));
        mockMvc.perform(get("/rentals/a1a8629e-ee35-4c42-9ef6-4a2b61381164"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(rentals.get(1).getPrice()))
                .andExpect(jsonPath("$.useKm").value(rentals.get(1).getUseKm()));

        when(rentalRepository.findById(UUID.fromString("b6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.empty());
        mockMvc.perform(get("/rentals/b6a8669e-ee95-4c42-9ef6-4a9b61380164")).andExpect(status().isNotFound());
    }

    @Test
    public void deleteById() throws Exception {
        doNothing().when(rentalRepository).deleteById(any());
        mockMvc.perform(delete("/rentals/a1a8629e-ee35-4c42-9ef6-4a2b61381164/delete")).andExpect(status().isNoContent());
    }

    /*@Test
    public void createNewRental() throws Exception {
        when(rentalRepository.save(any(Rental.class))).thenAnswer((invocation) -> invocation.getArgument(0));
        Rental newRental = Rental.builder()
                .price(1000)
                .startDate(LocalDate.of(2022, Calendar.AUGUST, 10))
                .endDate(LocalDate.of(2022, Calendar.AUGUST, 25))
                .useKm(5)
                .customer(new Customer())
                .vehicle(new Bike())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/rentals/save")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(newRental)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(newRental.getPrice()))
                .andExpect(jsonPath("$.useKm").value(newRental.getUseKm()));
    }

    @Test
    public void replaceRentalTest() throws Exception {
        Rental updatedRental = Rental.builder()
                .price(10000)
                .startDate(LocalDate.of(2022, Calendar.OCTOBER, 10))
                .endDate(LocalDate.of(2022, Calendar.OCTOBER, 25))
                .useKm(200)
                .customer(new Customer())
                .vehicle(Bike.builder()
                        .vehicleType(VehicleType.BIKE)
                        .licensePlate("ABC-123")
                        .seats(1)
                        .mileage(1234)
                        .mileageLimit(200)
                        .horsePower(95)
                        .gearBoxType(GearboxType.MANUAL)
                        .fuelType(FuelType.PETROL_PREMIUM)
                        .strokeType(BikeStrokeType.FOUR_STROKE)
                        .build())
                .build();
        when(rentalRepository.findById(UUID.fromString("a6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.of(rentals.get(2)));
        when(rentalRepository.save(any(Rental.class))).thenReturn(updatedRental);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/rentals/a6a8669e-ee95-4c42-9ef6-4a9b61380164/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRental)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(rentals.get(2).getPrice()))
                .andExpect(jsonPath("$.useKm").value(rentals.get(2).getUseKm()));
    }*/
}
