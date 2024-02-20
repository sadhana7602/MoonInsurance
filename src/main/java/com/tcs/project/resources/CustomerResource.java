package com.tcs.project.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.project.resource.Customer;
import com.tcs.project.services.CustomerService;



@RestController
@RequestMapping("/customer")
public class CustomerResource {
	
	@Autowired
	CustomerService customerservice;
	
	
	@GetMapping("/customerbyid/{id}")
	public Customer getClientByIdHandler(@PathVariable("Customerid") int id)  {
		return customerservice.getCustomerById(id);
	}
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE,
	value = "/addcustomer")
	public Customer RegisterCustomerHandler(@RequestBody Customer customer)  {
		System.out.println("hi");
		return customerservice.registerCustomer(customer);
	}
	
	@PutMapping(consumes =MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/updatecustomer")
	public Customer UpdateCustomerHandler( @RequestBody Customer customer)  {
		
		return customerservice.updateCustomer(customer);
	}
	
	@DeleteMapping("/deletecustomer/{id}")
	public boolean deleteCustomerHandler(@PathVariable("Customerid") int id){
		return customerservice.deleteCustomer(id);
	}
	
}
