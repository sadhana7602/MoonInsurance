package com.tcs.project.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.repository.PurchasedPolicyRepository;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.resource.PurchasedPolicies;



@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerrepository;
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	PolicyProductRepository policyproductrepository;
	@Autowired
	PurchasedPolicyRepository purchasedpolicyrepository;
	
	public Customer getCustomerById(int id) {
		
		Optional<Customer> customer = customerrepository.findById(id);
		return customer.get();
	}
	
	public Customer registerCustomer(Customer customer) {
		SimpleMailMessage message = new SimpleMailMessage();
        
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Registration Confirmation");
        message.setText("Dear "+customer.getName()+",\n\n\nThank you for registering with us.Below are the details:\n\n"+
        "CustomerId : "+customer.getCustomerid()+
        "Customer Name : "+customer.getName()+
        "Customer Phone number: "+customer.getPhoneNumber()+
        "Customer Address: "+customer.getAddress()
        +"\n\n\nBest regards,\nMoon Insurance");
        
        mailSender.send(message);
    
	
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
    public ArrayList<Object[]> allCustomerPurchasedPolicies(Customer customer) {
    	
    			ArrayList<Object[]> policyDetails = new ArrayList<>();
    			ArrayList<PurchasedPolicies> purchasedpolicies= (ArrayList<PurchasedPolicies>) purchasedpolicyrepository.findAll();
    	
    			for (PurchasedPolicies policy : purchasedpolicies) {
    				if(policy.getCustomerId()==customer.getCustomerid())
    				{     Object[] details = new Object[7];
	    					details[0] = policy.getPolicyNo();
	    					details[5] = policy.getEffectiveDate();
	    					details[6] = policy.getExpiryDate();
	    					details[7] = policy.getNominee();
	    	
	    				
	    					details[1] = customer.getCustomerid();
	    					details[2] = customer.getName();
	    	
	    					PolicyProduct product = policyproductrepository.findById(policy.getProductId()).orElse(null);
	    					details[3] = product.getProductTier();
	    					details[4] = product.getCoverageAmount();
	    	
	    				policyDetails.add(details);
	    				
    				}
    			   }
    			return policyDetails;
    		}
}
