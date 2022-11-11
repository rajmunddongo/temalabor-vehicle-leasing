package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository customerRepository;
	private final RentalRepository rentalRepository;
	
	/**
	 * Finds the given customer's latest rental. The latest rental's start date is the latest among all rentals owned by the customer
	 * 
	 * @param customer the customer whose latest rental is needed
	 * @return the latest rental
	 */
	public Rental findLatestRental(@NonNull Customer customer) {
		Collection<Rental> customersRentals = rentalRepository.findByCustomer(customer).stream().sorted(Comparator.comparing(Rental::getStartDate).reversed()).collect(Collectors.toList());
		
		return customersRentals.stream().findFirst().get();
	}
	
	/**
	 * Calculates the given customer's rentals number
	 * 
	 * @param customer the customer whose number of rentals needed
	 * @return number of rentals
	 */
	public int numberOfRentals(@NonNull Customer customer) {
		return rentalRepository.findByCustomer(customer).size();
	}
	
	/**
	 * Finds all inactive customers. A customer is inactive if it's last rental's start date is 2 years (or more) behind than the current date
	 * 
	 * @return the list of inactive customers
	 */
	public List<Customer> findInactiveCustomers() {
		Date currentDate = new Date();
		long differenceInMilliseconds;
		
		List<Customer> inactiveCustomers = customerRepository.findAll();
		
		for(Customer c : inactiveCustomers) {
			differenceInMilliseconds = currentDate.getTime() - findLatestRental(c).getStartDate().getTime();
			
			//Delete active customers from the list
			if(TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds) / 365 <= 2) {inactiveCustomers.remove(c);}
		}
		
		return inactiveCustomers;
	}
	
	/**
	 * Finds the desired number of active customers. A customer's activeness is measured by the number of rentals.
	 * 
	 * @param topHowMany desired number of active customers
	 * @return the list of the most active customers
	 */
	public List<Customer> findMostActiveCustomers(int topHowMany) {
		//Invalid parameter check
		if(topHowMany <= 0 || topHowMany > customerRepository.count()) return null;
		
		List<Customer> allCustomers = customerRepository.findAll();
		
		List<Customer> mostActiveCustomers = new ArrayList<>();
		
		//Natural order tree set (ascending)
		Set<Integer> quantitiesSet = new TreeSet<>();
		
		//Populate TreeSet
		customerRepository.findAll().forEach(c -> {quantitiesSet.add(Integer.valueOf(numberOfRentals(c)));});
				
		if(!quantitiesSet.isEmpty()) {
			//Convert set to array
			Integer[] quantitiesArray = new Integer[quantitiesSet.size()];
			quantitiesSet.toArray(quantitiesArray);
			
			for(int i = quantitiesArray.length; i >= quantitiesArray.length - topHowMany; i--) {
				for(Customer c : allCustomers) {
					if(numberOfRentals(c) == i) {mostActiveCustomers.add(c);}
				}
			}
		}
		
		return mostActiveCustomers;
	}
	
	/**
	 * Increases the amount of bonus points with the given amount for the desired number of active customers. 
	 * 
	 * @param topHowMany desired number of active customers
	 * @param amount number of extra bonus points
	 */
	public void giveBonusPointsToMostActiveCustomers(int topHowMany, int amount) {
		if(topHowMany <= 0 || topHowMany > customerRepository.count() || amount <= 0) return;
		
		List<Customer> winnersList = findMostActiveCustomers(topHowMany);
		
		winnersList.forEach(w -> {
			if(w.getBonusPoints() + amount > Integer.MAX_VALUE) w.setBonusPoints(Integer.MAX_VALUE);
			else w.setBonusPoints(w.getBonusPoints() + amount);
			
			customerRepository.save(w);
		});
	}
}
