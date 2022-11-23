package hu.bme.aut.temalab.temalaborvehicleleasing.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.Customer;
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
		
		//Remove not active customers
		for(int i = numberOfRentalArray.length - 1; i > numberOfRentalArray.length - 1 - topHowMany; i--) {
			for(Customer c : customers) {
				if(rentalRepository.findByCustomer(c).size() == numberOfRentalArray[i]) {mostActiveCustomers.add(c);}
			}
		}
		
		return mostActiveCustomers;
	}
}