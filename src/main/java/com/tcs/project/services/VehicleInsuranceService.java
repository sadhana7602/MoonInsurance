package com.tcs.project.services;

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
    public VehicleInsurance createVehicleInsurance(VehicleInsurance vehicleInsurance) {
        //return vehicleInsuranceRepository.save(vehicleInsurance);
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	VehicleInsurance purchasedPolicy=(VehicleInsurance) vehicleInsuranceRepository.save(vehicleInsurance);
        Customer customer=customerrepository.getById(vehicleInsurance.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(vehicleInsurance.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Vehicle Insurance Policy Purchase Confirmation");
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
    public ArrayList<Object[]> allAdminPurchasedPolicies() {

		ArrayList<Object[]> policyDetails = new ArrayList<>();
		ArrayList<VehicleInsurance> purchasedpolicies = (ArrayList<VehicleInsurance>) vehicleInsuranceRepository.findAll();

		for (VehicleInsurance policy : purchasedpolicies) {
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
		for (VehicleInsurance policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return policyDetails;
	}
}
