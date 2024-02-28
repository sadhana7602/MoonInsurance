package com.tcs.project.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

import com.tcs.project.resource.Customer;
import com.tcs.project.resource.LoginDto;
import com.tcs.project.services.CustomerService;



@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/customer")
public class CustomerResource {
	
	@Autowired
	CustomerService customerservice;
	
	@GetMapping("/all")
	public List<Customer> getAllCustomer()  {
		return customerservice.getAllCustomer();
	}
	
	@GetMapping("/get/{id}")
	public Customer getCustomerById(@PathVariable("id") int id)  {
		return customerservice.getCustomerById(id);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE,
	value = "/add")
	public boolean AddCustomer(@RequestBody Customer customer)  {
		return customerservice.registerCustomer(customer);
	}
	
	@PutMapping(consumes =MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/update")
	public Customer updateCustomer( @RequestBody Customer customer)  {
		return customerservice.updateCustomer(customer);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deleteCustomer(@PathVariable("id") int id){
		return customerservice.deleteCustomer(id);
	}
//	@GetMapping("/customerall")
//	public ResponseEntity<ArrayList<Object[]>> allCustomerPurchasedPolicies(@RequestBody Customer customer){
//		return new ResponseEntity<ArrayList<Object[]>> (customerservice.allCustomerPurchasedPolicies(customer) ,HttpStatus.OK);
//	}
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			value = "/login")
			public String Login(@RequestBody LoginDto logindto)  {
				return customerservice.Login(logindto);
			}
			
	
}
