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
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.repository.VehicleInsuranceRepository;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.HealthInsurance;
import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.resource.VehicleDto;
import com.tcs.project.resource.VehicleInsurance;

@Service
public class VehicleInsuranceService {
 
    @Autowired
    private VehicleInsuranceRepository vehicleInsuranceRepository;
    @Autowired
	PolicyProductRepository policyproductrepository;
	@Autowired
	CustomerRepository customerrepository;
	@Autowired
    private JavaMailSender mailSender;
 
    public List<VehicleInsurance> getAllVehicleInsurances() {
        return vehicleInsuranceRepository.findAll();
    }
    public Optional<VehicleInsurance> getVehicleInsuranceById(int id) {
        return vehicleInsuranceRepository.findById(id);
    }
    public boolean createVehicleInsurance(VehicleDto vehicledto) {
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	VehicleInsurance vehicleinsurance = new VehicleInsurance();
    	PolicyProduct pp= policyproductrepository.getById(vehicledto.getProductId());
    	vehicleinsurance.setProductId(vehicledto.getProductId());
    	vehicleinsurance.setCustomerId(vehicledto.getCustomerId());
    	vehicleinsurance.setGender(vehicledto.getGender());
    	vehicleinsurance.setAge(vehicledto.getAge());
    	vehicleinsurance.setNominee(vehicledto.getNominee());
    	vehicleinsurance.setMake(vehicledto.getMake());
    	vehicleinsurance.setModel(vehicledto.getModel());
    	vehicleinsurance.setFcDate(vehicledto.getFcDate());
    	
    	vehicleinsurance.setCoverageBalance(pp.getCoverageAmount());
    	LocalDate currentDate = LocalDate.now();
    	LocalDate enddate = currentDate.plusYears(pp.getTenure());
    	vehicleinsurance.setEffectiveDate(Date.valueOf(currentDate));
    	vehicleinsurance.setExpiryDate(Date.valueOf(enddate));
    
    	
    	VehicleInsurance purchasedPolicy=(VehicleInsurance) vehicleInsuranceRepository.save(vehicleinsurance);
        Customer customer=customerrepository.getById(vehicleinsurance.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(vehicleinsurance.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Vehicle Insurance Policy Purchase Confirmation");
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
    public VehicleInsurance updateVehicleInsurance(VehicleInsurance updatedVehicleInsurance) {      
        
        Optional<VehicleInsurance> optional = vehicleInsuranceRepository.findById(updatedVehicleInsurance.getPolicyId());
        VehicleInsurance tempVehicleInsurance = optional.get();
        tempVehicleInsurance.setAge(updatedVehicleInsurance.getAge());
        tempVehicleInsurance.setGender(updatedVehicleInsurance.getGender());
        tempVehicleInsurance.setNominee(updatedVehicleInsurance.getNominee());
    	tempVehicleInsurance.setMake(updatedVehicleInsurance.getMake());
    	tempVehicleInsurance.setModel(updatedVehicleInsurance.getModel());
    	tempVehicleInsurance.setFcDate(updatedVehicleInsurance.getFcDate());
    	
		return (VehicleInsurance) vehicleInsuranceRepository.save(tempVehicleInsurance);
    }
    public boolean deleteVehicleInsurance(int id) {
        vehicleInsuranceRepository.deleteById(id);
        return true;
    }
    public List<PolicyProduct> allVehiclePolicies() {

		List<PolicyProduct> purchasedpolicies = policyproductrepository.findByProductName("Auto");
		
		for (PolicyProduct policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return purchasedpolicies;
	}
}
