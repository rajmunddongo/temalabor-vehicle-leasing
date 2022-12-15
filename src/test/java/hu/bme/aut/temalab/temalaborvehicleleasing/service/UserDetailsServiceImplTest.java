package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.UserRole;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class UserDetailsServiceImplTest {
    
	@Autowired
    private UserRepository userRepository;
    
	@Autowired
    private UserDetailsServiceImpl udsi;

    @BeforeEach
    void setUp() {
       
    	userRepository.save(
                Customer.builder()
                        .firstName("Got")
                        .lastName("You")
                        .username("gotcha")
                        .password("getthis")
                        .drivingLicenceNumber("AAAAAAAA2")
                        .bonusPoints(5)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        
    	userRepository.deleteAll();
    }

    @Test
    void userDetailsServiceImplTest() {
        
    	UserDetails user = udsi.loadUserByUsername("gotcha");
        
    	assertTrue(user.getAuthorities().contains(UserRole.ROLE_CUSTOMER));
        assertTrue(user.getPassword().contains("getthis"));

    }

    @Test
    void usernameIsEmptyTest() {
        
    	Executable executable = () -> udsi.loadUserByUsername("InvalidUserName");
        
    	Exception exception = assertThrows(UsernameNotFoundException.class, executable);
        
    	assertTrue(exception.getMessage().contains("User not found!"));
    }
}
