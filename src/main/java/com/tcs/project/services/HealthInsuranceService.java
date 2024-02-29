package com.tcs.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.HealthInsuranceRepository;
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.HealthDto;
import com.tcs.project.resource.HealthInsurance;
import com.tcs.project.resource.PolicyProduct;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
@Service
public class HealthInsuranceService {
 
    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;
    @Autowired
	PolicyProductRepository policyproductrepository;
	@Autowired
	CustomerRepository customerrepository;
	@Autowired
    private JavaMailSender mailSender;
    
    public List<HealthInsurance> getAllHealthInsurances() {
        return healthInsuranceRepository.findAll();
    }
    public Optional<HealthInsurance> getHealthInsuranceById(int id) {
        return healthInsuranceRepository.findById(id);
    }
    public boolean createHealthInsurance(HealthDto healthDto) {
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	HealthInsurance healthInsurance= new HealthInsurance();
    	PolicyProduct pp= policyproductrepository.getById(healthDto.getProductId());
    	healthInsurance.setProductId(healthDto.getProductId());
    	healthInsurance.setCustomerId(healthDto.getCustomerId());
    	healthInsurance.setGender(healthDto.getGender());
    	healthInsurance.setAge(healthDto.getAge());
    	healthInsurance.setNominee(healthDto.getNominee());
    	healthInsurance.setCoverageBalance(pp.getCoverageAmount());
    	LocalDate currentDate = LocalDate.now();
    	LocalDate enddate = currentDate.plusYears(pp.getTenure());
    	healthInsurance.setEffectiveDate(Date.valueOf(currentDate));
    	healthInsurance.setExpiryDate(Date.valueOf(enddate));
    	
    	
    	HealthInsurance purchasedPolicy=(HealthInsurance) healthInsuranceRepository.save(healthInsurance);
        Customer customer=customerrepository.getById(healthInsurance.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(healthInsurance.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Health Insurance Policy Purchase Confirmation");
        message.setText("Dear "+customer.getName()+",\n\nThank you for purchasing a policy. Below are the details:\n\nPolicyNo: "+purchasedPolicy.getPolicyId()
        		+"\nPolicy Name :"+policy.getProductName()
        		+"\nPremium:"+policy.getProductPremium()
        		+"\nEffective Date :"+purchasedPolicy.getEffectiveDate()
                +"\nExpire Date :"+purchasedPolicy.getExpiryDate()
                +"\nNominee:"+purchasedPolicy.getNominee()
                
     
        		+"\n\n\n\n\nBest regards,\nMoon Insurance");
        
        mailSender.send(message);
    
		return true;
    }
    public HealthInsurance updateHealthInsurance(HealthInsurance updatedHealthInsurance) {
    	
    	Optional<HealthInsurance> optional = healthInsuranceRepository.findById(updatedHealthInsurance.getPolicyId());
    	HealthInsurance tempHealthInsurance = optional.get();
    	tempHealthInsurance.setGender(updatedHealthInsurance.getGender());
    	tempHealthInsurance.setAge(updatedHealthInsurance.getAge());
    	tempHealthInsurance.setNominee(updatedHealthInsurance.getNominee());

		return (HealthInsurance) healthInsuranceRepository.save(tempHealthInsurance);
    }
 
    public boolean deleteHealthInsurance(int id) {
        healthInsuranceRepository.deleteById(id);
        return true;
    }

	public List<PolicyProduct> allHealthPolicies() {

		List<PolicyProduct> purchasedpolicies = policyproductrepository.findByProductName("Health");
		
		for (PolicyProduct policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return purchasedpolicies;
	}
}


