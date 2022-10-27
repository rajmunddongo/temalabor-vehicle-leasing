package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hu.bme.aut.temalab.temalaborvehicleleasing.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
	   @Autowired
	   private UserRepository userRepository;
	   
	   @Autowired 
	   UserService userService;
	
		@BeforeEach
	    void setUp() {
	    }

	    @AfterEach
	    void tearDown() {
	    }
	    
	    @Test
	    void giveOutBonusPointsToMostActiveCustomers() {
	    }
}
