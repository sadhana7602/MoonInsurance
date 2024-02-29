package com.tcs.project.services;

import java.sql.Date;
import java.time.LocalDate;
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
import com.tcs.project.resource.HomeDto;
import com.tcs.project.resource.HomeInsurance;
import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.resource.VehicleInsurance;

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
    public boolean createHomeInsurance(HomeDto homedto) {
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	HomeInsurance homeinsurance = new HomeInsurance();
    	PolicyProduct pp= policyproductrepository.getById(homedto.getProductId());
    	homeinsurance.setProductId(homedto.getProductId());
    	homeinsurance.setCustomerId(homedto.getCustomerId());
    	homeinsurance.setGender(homedto.getGender());
    	homeinsurance.setAge(homedto.getAge());
    	homeinsurance.setNominee(homedto.getNominee());
    	homeinsurance.setHomeType(homedto.getHomeType());
    	homeinsurance.setBuiltArea(homedto.getBuildArea());
    	homeinsurance.setAssetValue(homedto.getAssetValue());
    	
    	homeinsurance.setCoverageBalance(pp.getCoverageAmount());
    	LocalDate currentDate = LocalDate.now();
    	LocalDate enddate = currentDate.plusYears(pp.getTenure());
    	homeinsurance.setEffectiveDate(Date.valueOf(currentDate));
    	homeinsurance.setExpiryDate(Date.valueOf(enddate));
    	    	
    	HomeInsurance purchasedPolicy=(HomeInsurance) homeInsuranceRepository.save(homeinsurance);
        Customer customer=customerrepository.getById(homeinsurance.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(homeinsurance.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Home Insurance Policy Purchase Confirmation");
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

    public List<PolicyProduct> allHomePolicies() {

		List<PolicyProduct> purchasedpolicies = policyproductrepository.findByProductName("Home");
		
		for (PolicyProduct policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return purchasedpolicies;
	}
}
