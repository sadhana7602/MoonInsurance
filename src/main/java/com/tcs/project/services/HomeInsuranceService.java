package com.tcs.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.HomeInsuranceRepository;
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.HomeInsurance;
import com.tcs.project.resource.PolicyProduct;

@Service
public class HomeInsuranceService {
	@Autowired
    private HomeInsuranceRepository homeInsuranceRepository;
    @Autowired
	PolicyProductRepository policyproductrepository;
	@Autowired
	CustomerRepository customerrepository;
	@Autowired
    private JavaMailSender mailSender;
    
    public List<HomeInsurance> getAllHomeInsurances() {
        return homeInsuranceRepository.findAll();
    }
    public Optional<HomeInsurance> getHomeInsuranceById(int id) {
        return homeInsuranceRepository.findById(id);
    }
    public HomeInsurance createHomeInsurance(HomeInsurance homeInsurance) {
        //return homeInsuranceRepository.save(homeInsurance);
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	HomeInsurance purchasedPolicy=(HomeInsurance) homeInsuranceRepository.save(homeInsurance);
        Customer customer=customerrepository.getById(homeInsurance.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(homeInsurance.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Home Insurance Policy Purchase Confirmation");
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
    public HomeInsurance updateHomeInsurance(HomeInsurance updatedHomeInsurance) {
    	
    	Optional<HomeInsurance> optional = homeInsuranceRepository.findById(updatedHomeInsurance.getPolicyId());
    	HomeInsurance tempHomeInsurance = optional.get();
    	tempHomeInsurance.setGender(updatedHomeInsurance.getGender());
    	tempHomeInsurance.setAge(updatedHomeInsurance.getAge());
    	tempHomeInsurance.setNominee(updatedHomeInsurance.getNominee());

		return (HomeInsurance) homeInsuranceRepository.save(tempHomeInsurance);
    }
 
    public boolean deleteHomeInsurance(int id) {
        homeInsuranceRepository.deleteById(id);
        return true;
    }

	public ArrayList<Object[]> allAdminPurchasedPolicies() {

		ArrayList<Object[]> policyDetails = new ArrayList<>();
		ArrayList<HomeInsurance> purchasedpolicies = (ArrayList<HomeInsurance>) homeInsuranceRepository.findAll();

		for (HomeInsurance policy : purchasedpolicies) {
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
		
		for (HomeInsurance policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return policyDetails;
	}
}
