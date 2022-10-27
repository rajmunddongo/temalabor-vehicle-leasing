package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import org.springframework.stereotype.Service;

import hu.bme.aut.temalab.temalaborvehicleleasing.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class UserService {
	private final UserRepository userRepository;
	
	/**
     * Most active costumer gets M bonus points which can reduce the price of the next rental
     * 
     * @param topHowMany number of top customers
     * @param amount number of bonus points
     */
	public void giveOutBonusPointsToMostActiveCustomers(int topHowMany, int amount) {
		if(topHowMany <= 0 || topHowMany > userRepository.count() || amount <= 0) {return;}
		
		userRepository.findMostActiveCustomers(topHowMany).forEach(c -> {
			if(c.getBonusPoints() + amount > Integer.MAX_VALUE) {c.setBonusPoints(Integer.MAX_VALUE);}
			else {c.setBonusPoints(c.getBonusPoints() + amount);}
			
			userRepository.save(c);
		});
	}
}
