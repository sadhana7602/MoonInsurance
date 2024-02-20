package com.tcs.project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.resource.Customer;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerrepository;
	
	public Customer getCustomerById(int id) {
		
		Optional<Customer> customer = customerrepository.findById(id);
		return customer.get();
	}
	
	public Customer registerCustomer(Customer customer) {
	
		return customerrepository.save(customer);
		
	}
	
	public Customer updateCustomer(Customer customer) {
		Optional<Customer> c = Optional.ofNullable(customerrepository.getById(customer.getCustomerid()));
		Customer temp = c.get();
		temp.setName(customer.getName());
		temp.setDateOfBirth(customer.getDateOfBirth());
		temp.setAddress(customer.getAddress());
		temp.setEmail(customer.getEmail());
		temp.setPassword(customer.getPassword());
		temp.setPhoneNumber(customer.getPhoneNumber());
		 return customerrepository.save(temp);
		
				
	}
	
    public boolean deleteCustomer(int id) {
    	customerrepository.deleteById(id);
    	return true;
    }
}
