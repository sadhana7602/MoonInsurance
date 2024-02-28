package com.tcs.project.services;

import java.util.ArrayList;
import java.util.List;
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
import com.tcs.project.resource.LoginDto;
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
	
	public List<Customer> getAllCustomer() {
		
		return customerrepository.findAll();
	}
	
	public Customer getCustomerById(int id) {
		
		Optional<Customer> customer = customerrepository.findById(id);
		return customer.get();
	}
	
	public boolean registerCustomer(Customer customerD) {
		
    
	
		Customer customer = customerrepository.save(customerD);
		SimpleMailMessage message = new SimpleMailMessage();
        
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Registration Confirmation");
        message.setText("Dear "+customer.getName()+",\n\n\nThank you for registering with us.Below are the details:\n\n"+
        "\nCustomerId : "+customer.getCustomerid()+
        "\nCustomer Name : "+customer.getName()+
        "\nCustomer Phone number: "+customer.getPhone()+
        "\nCustomer Address: "+customer.getAddress()
        +"\n\n\nBest regards,\nMoon Insurance");
        
        System.out.println(customer);
        
        mailSender.send(message);
        return true;
		
		
	}
	
	public Customer updateCustomer(Customer customer) {
		Optional<Customer> c = Optional.ofNullable(customerrepository.getById(customer.getCustomerid()));
		Customer temp = c.get();
		temp.setName(customer.getName());
		temp.setDateOfBirth(customer.getDateOfBirth());
		temp.setAddress(customer.getAddress());
		temp.setEmail(customer.getEmail());
		temp.setPassword(customer.getPassword());
		temp.setPhone(customer.getPhone());
		 return customerrepository.save(temp);
		
				
	}
	
    public boolean deleteCustomer(int id) {
    	customerrepository.deleteById(id);
    	return true;
    }
//    public ArrayList<Object[]> allCustomerPurchasedPolicies(Customer customer) {
//    	
//    			ArrayList<Object[]> policyDetails = new ArrayList<>();
//    			ArrayList<PurchasedPolicies> purchasedpolicies= (ArrayList<PurchasedPolicies>) purchasedpolicyrepository.findAll();
//    	
//    			for (PurchasedPolicies policy : purchasedpolicies) {
//    				if(policy.getCustomerId()==customer.getCustomerid())
//    				{     Object[] details = new Object[7];
//	    					details[0] = policy.getPolicyNo();
//	    					details[5] = policy.getEffectiveDate();
//	    					details[6] = policy.getExpiryDate();
//	    					details[7] = policy.getNominee();
//	    	
//	    				
//	    					details[1] = customer.getCustomerid();
//	    					details[2] = customer.getName();
//	    	
//	    					PolicyProduct product = policyproductrepository.findById(policy.getProductId()).orElse(null);
//	    					details[3] = product.getProductTier();
//	    					details[4] = product.getCoverageAmount();
//	    	
//	    				policyDetails.add(details);
//	    				
//    				}
//    			   }
//    			return policyDetails;
//    		}
    public String Login(LoginDto logindto) {
    	Optional<Customer> customer=customerrepository.findOneByEmailAndPassword(logindto.getEmail(), logindto.getPassword());
    	if (customer.isPresent()) {
            return "true";
        } else {
            return "false";
        }
    }
}
