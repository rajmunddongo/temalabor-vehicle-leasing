package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.Rental;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.CustomerRepository;
import hu.bme.aut.temalab.temalaborvehicleleasing.repository.RentalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class CustomerService {
	private final CustomerRepository customerRepository;
	private final RentalRepository rentalRepository;
	
	/**
	 * Finds the desired number of active customers. Activeness is measured by the number of rentals.
	 * 
	 * @param topHowMany desired number of active customers
	 * @return collection of active customers 
	 */
	public Collection<Customer> findMostActiveCustomers(int topHowMany) {
		//Parameter check
		if(topHowMany <= 0 || topHowMany > customerRepository.findAll().size()) return null;
		
		Collection<Customer> mostActiveCustomers = new ArrayList<>();
		Collection<Customer> customers = customerRepository.findAll();
		
		//Ascending order set
		Set<Integer> numberOfRentalSet = new TreeSet<>();
		
		//Populate tree set
		customers.forEach(c -> {
			numberOfRentalSet.add(rentalRepository.findByCustomer(c).size());
		});
		
		//Convert tree set into an array
		Integer[] numberOfRentalArray = new Integer[numberOfRentalSet.size()];
		numberOfRentalSet.toArray(numberOfRentalArray);
		
		//Add active customers
		for(int i = numberOfRentalArray.length - 1; i > numberOfRentalArray.length - 1 - topHowMany; i--) {
			for(Customer c : customers) {
				if(rentalRepository.findByCustomer(c).size() == numberOfRentalArray[i]) {mostActiveCustomers.add(c);}
			}
		}
		
		return mostActiveCustomers;
	}
	
	/**
	 * Increases the given number of most active customers' bonus point by the given amount
	 * 
	 * @param amount quantity of the bonus points
	 * @param topHowMany number of most active customers
	 */
	public void giveOutBonusPoints(int amount, int topHowMany) {
		//Parameter check
		if(amount <= 0 || topHowMany <=0 || topHowMany > customerRepository.findAll().size()) return;
		
		Collection<Customer> mostActiveCustomers = findMostActiveCustomers(topHowMany);
		
		//Increment bonus points
		mostActiveCustomers.forEach(c -> {
			//If c.getBonusPoints()+amount > Integer.MAX_VALUE then it will start counting from Integer.MIN_VALUE which is a negative number
			if(c.getBonusPoints() + amount < 0) {c.setBonusPoints(Integer.MAX_VALUE);}
			else {c.setBonusPoints(c.getBonusPoints() + amount);}
			customerRepository.save(c);
		});
	}
	
	/**
	 * Finds inactive customers in the database. A customer is inactive if his/her last rental's start date is more than 2 years behind than the current date
	 * 
	 * @return a collection of inactive customers
	 */
	public Collection<Customer> findInactiveCustomers() {
		
		List<Customer> customers = customerRepository.findAll();
		List<Customer> inactiveCustomers = new ArrayList<>();
		
		ArrayList<Rental> rentals;
		
		for(Customer c : customers) {
			rentals = new ArrayList<>(rentalRepository.findByCustomer(c));
			
			//Ascending order of rentals in case of somebody appended latter rentals manually
			rentals.sort(Comparator.comparing(Rental::getStartDate));
			
			if(rentals != null && !rentals.isEmpty()) {
				if(ChronoUnit.YEARS.between(rentals.get(rentals.size() - 1).getStartDate(), LocalDate.now()) > 2) inactiveCustomers.add(c);
			}
		}
		
		return inactiveCustomers;
	}
}
