package hu.bme.aut.temalab.temalaborvehicleleasing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerRepository customerRepository;
	
	private List<Customer> customers;
	
	@BeforeEach
	void setUp() {
		
		customers = new ArrayList<>();
		
		customers.add(Customer.builder()
						.id(UUID.fromString("c6a8669e-ee95-4c42-9ef6-4a9b61380164"))
						.firstName("Homer")
						.lastName("Simpson")
						.username("homie")
						.password("duffbeer")
						.drivingLicenceNumber("AAAAAAAA")
						.build());
		
		customers.add(Customer.builder()
						.id(UUID.fromString("d6a8669e-ee95-4c42-9ef6-4a9b61380164"))
						.firstName("Marge")
						.lastName("Simpson")
						.username("margiee")
						.password("familylove")
						.drivingLicenceNumber("BBBBBBBB")
						.build());
		
		customers.add(Customer.builder()
						.id(UUID.fromString("e6a8669e-ee95-4c42-9ef6-4a9b61380164"))
						.firstName("Bart")
						.lastName("Simpson")
						.username("skateboardBoy")
						.password("vandalismisfun")
						.drivingLicenceNumber("CCCCCCCC")
						.build());
		
		customers.add(Customer.builder()
						.id(UUID.fromString("f6a8669e-ee95-4c42-9ef6-4a9b61380164"))
						.firstName("Lisa")
						.lastName("Simpson")
						.username("catlady")
						.password("beeinganerdisfun")
						.drivingLicenceNumber("DDDDDDDD")
						.build());
		
		customers.add(Customer.builder()
						.id(UUID.fromString("a6a8669e-ee95-4c42-9ef6-4a9b61380164"))
						.firstName("Maggie")
						.lastName("Simpson")
						.username("babygirl")
						.password("cuppcupp")
						.drivingLicenceNumber("EEEEEEEE")
						.build());
	}
	
	@Test
	public void findAllTest() throws Exception {
		
		when(customerRepository.findAll()).thenReturn(customers);
		
		mockMvc.perform(get("/customers/all")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(customers.size()));
	}
	

	@Test
	public void createCustomerTest() throws Exception {
		
		when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArgument(0));
		
		Customer newCustomer = Customer.builder()
								.firstName("Ádám")
								.lastName("Göbhardter")
								.username("ABCD123")
								.password("0000")
								.drivingLicenceNumber("NNNNNNNN")
								.build();
		
		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(post("/customers/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newCustomer)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName").value(newCustomer.getFirstName()))
					.andExpect(jsonPath("$.lastName").value(newCustomer.getLastName()))
					.andExpect(jsonPath("$.username").value(newCustomer.getUsername()))
					.andExpect(jsonPath("$.password").value(newCustomer.getPassword()))
					.andExpect(jsonPath("$.drivingLicenceNumber").value(newCustomer.getDrivingLicenceNumber()));
	}

	
	@Test
	public void findCustomerById() throws Exception {
		
		when(customerRepository.findById(UUID.fromString("e6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.of(customers.get(2)));
		
		mockMvc.perform(get("/customers/e6a8669e-ee95-4c42-9ef6-4a9b61380164"))
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.firstName").value(customers.get(2).getFirstName()))
							.andExpect(jsonPath("$.lastName").value(customers.get(2).getLastName()))
							.andExpect(jsonPath("$.username").value(customers.get(2).getUsername()))
							.andExpect(jsonPath("$.password").value(customers.get(2).getPassword()))
							.andExpect(jsonPath("$.drivingLicenceNumber").value(customers.get(2).getDrivingLicenceNumber()));
		
		when(customerRepository.findById(UUID.fromString("b6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.empty());
		
		mockMvc.perform(get("/customers/b6a8669e-ee95-4c42-9ef6-4a9b61380164")).andExpect(status().isNotFound());
	}


	@Test
	public void updateCustomersDataTest() throws Exception{
		
		Customer updatedCustomer = Customer.builder()
										.firstName("Béla")
										.lastName("Nagy")
										.username("belus13")
										.password("hahah")
										.drivingLicenceNumber("OOOOOOOO")
										.build();
		
		when(customerRepository.findById(UUID.fromString("a6a8669e-ee95-4c42-9ef6-4a9b61380164"))).thenReturn(Optional.of(customers.get(4)));
		when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
		
		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(post("/customers/a6a8669e-ee95-4c42-9ef6-4a9b61380164/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedCustomer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(updatedCustomer.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(updatedCustomer.getLastName()))
				.andExpect(jsonPath("$.username").value(updatedCustomer.getUsername()))
				.andExpect(jsonPath("$.password").value(updatedCustomer.getPassword()))
				.andExpect(jsonPath("$.drivingLicenceNumber").value(updatedCustomer.getDrivingLicenceNumber()));
	}


	@Test
	public void deleteCustomerTest() throws Exception {
		
		doNothing().when(customerRepository).deleteById(any());

		mockMvc.perform(delete("/customers/a6a8669e-ee95-4c42-9ef6-4a9b61380164/delete")).andExpect(status().isNoContent());
	}
}
