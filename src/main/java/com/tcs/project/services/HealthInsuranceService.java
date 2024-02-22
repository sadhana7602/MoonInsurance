package com.tcs.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.HealthInsuranceRepository;
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.HealthInsurance;
import com.tcs.project.resource.PolicyProduct;

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
    public HealthInsurance createHealthInsurance(HealthInsurance healthInsurance) {
        //return healthInsuranceRepository.save(healthInsurance);
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	HealthInsurance purchasedPolicy=(HealthInsurance) healthInsuranceRepository.save(healthInsurance);
        Customer customer=customerrepository.getById(healthInsurance.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(healthInsurance.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Health Insurance Policy Purchase Confirmation");
        message.setText("Dear "+customer.getName()+",\n\nThank you for purchasing a policy. Below are the details:\n\nPolicyNo: "+purchasedPolicy.getPolicyNo()
        		+"\nPolicy Name :"+policy.getProductName()
        		+"\nPremium:"+policy.getProductPremium()
        		+"\nEffective Date :"+purchasedPolicy.getEffectiveDate()
                +"\nExpire Date :"+purchasedPolicy.getExpiryDate()
                +"\nNominee:"+purchasedPolicy.getNominee()
                
     
        		+"\n\n\n\n\nBest regards,\nMoon Insurance");
        
        mailSender.send(message);
    
		return purchasedPolicy;
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

	public ArrayList<Object[]> allAdminPurchasedPolicies() {

		ArrayList<Object[]> policyDetails = new ArrayList<>();
		ArrayList<HealthInsurance> purchasedpolicies = (ArrayList<HealthInsurance>) healthInsuranceRepository.findAll();

		for (HealthInsurance policy : purchasedpolicies) {
			Object[] details = new Object[8];
			details[0] = policy.getPolicyNo();
			details[5] = policy.getEffectiveDate();
			details[6] = policy.getExpiryDate();
			details[7] = policy.getNominee();

			Customer customer = customerrepository.findById(policy.getCustomerId()).orElse(null);
			details[1] = customer.getCustomerid();
			details[2] = customer.getName();

			PolicyProduct product = policyproductrepository.findById(policy.getProductId()).orElse(null);
			details[3] = product.getProductTier();
			details[4] = product.getCoverageAmount();

			policyDetails.add(details);
		}
		
		for (HealthInsurance policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return policyDetails;
	}
}


