package com.tcs.project.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.repository.PurchasedPolicyRepository;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.resource.PurchasedPolicies;

@Service
public class PurchasedPolicyService {

	@Autowired
	 PurchasedPolicyRepository purchasedpolicyrepository;
	@Autowired
	PolicyProductRepository policyproductrepository;
	@Autowired
	CustomerRepository customerrepository;
	@Autowired
    private JavaMailSender mailSender;

	public ArrayList<PurchasedPolicies> allPurchasedPolicies() {

		ArrayList<PurchasedPolicies> purchasedPolicies = (ArrayList<PurchasedPolicies>) purchasedpolicyrepository
				.findAll();
		return purchasedPolicies;
	}

	public PurchasedPolicies getPurchasedPolicyById(Integer policyId) {

		Optional<PurchasedPolicies> purchasedPolicies = purchasedpolicyrepository.findById(policyId);
		return purchasedPolicies.get();
	}

	public PurchasedPolicies createPurchasedPolicy(PurchasedPolicies purchasedPolicyD) {
		SimpleMailMessage message = new SimpleMailMessage();
		PurchasedPolicies purchasedPolicy=(PurchasedPolicies) purchasedpolicyrepository.save(purchasedPolicyD);
        Customer customer=customerrepository.getById(purchasedPolicy.getCustomerId());
        PolicyProduct policy=policyproductrepository.getById(purchasedPolicy.getProductId());
		message.setFrom("javafsdgroup@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Policy Purchase Confirmation");
        message.setText("Dear "+customer.getName()+",\n\nThank you for purchasing a policy. Below are the details:\n\nPolicyNo: "+purchasedPolicy.getPolicyNo()
        		+"\nPolicy Name :"+policy.getProductName()
        		+"\nPremium:"+policy.getProductPremium()
        		+"\nEffective Date :"+purchasedPolicy.getEffectiveDate()
                +"\nExpire Date :"+purchasedPolicy.getExpiryDate()
                +"\nNominee:"+purchasedPolicy.getNominee()
                
     
        		+"\\n\\n\\n\n\nBest regards,\nMoon Insurance");
        
        mailSender.send(message);
    
		return purchasedPolicy;

	}

	public PurchasedPolicies updatePurchasedPolicy(PurchasedPolicies purchasedPolicy) {

		Optional<PurchasedPolicies> optional = purchasedpolicyrepository.findById(purchasedPolicy.getProductId());
		PurchasedPolicies tempPolicy = optional.get();

//    	tempPolicy.setCoverageDescription(purchasedPolicy.getCoverageDescription());
//    	tempPolicy.setCoverageAmount(purchasedPolicy.getCoverageAmount());
//    	tempPolicy.setProductCode(purchasedPolicy.getProductCode());
//    	tempPolicy.setTenure(purchasedPolicy.getTenure());
//    	tempPolicy.setProductName(purchasedPolicy.getProductName());
//    	tempPolicy.setProductPremium(purchasedPolicy.getProductPremium());
//    	tempPolicy.setProductTier(purchasedPolicy.getProductTier());
		tempPolicy.setNominee(purchasedPolicy.getNominee());
		tempPolicy.setEffectiveDate(purchasedPolicy.getEffectiveDate());
		tempPolicy.setExpiryDate(purchasedPolicy.getExpiryDate());

		return (PurchasedPolicies) purchasedpolicyrepository.save(tempPolicy);
	}

	public boolean deletePolicyProduct(Integer policyId) {
		purchasedpolicyrepository.deleteById(policyId);
		return true;
	}

	public ArrayList<Object[]> allAdminPurchasedPolicies() {

		ArrayList<Object[]> policyDetails = new ArrayList<>();
		ArrayList<PurchasedPolicies> purchasedpolicies = (ArrayList<PurchasedPolicies>) purchasedpolicyrepository
				.findAll();

		for (PurchasedPolicies policy : purchasedpolicies) {
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
		
		for (PurchasedPolicies policy : purchasedpolicies) {
			System.out.println(policy);
		}
		return policyDetails;
	}


}
