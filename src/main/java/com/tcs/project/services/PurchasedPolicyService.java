package com.tcs.project.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.PurchasedPolicyRepository;
import com.tcs.project.resource.PurchasedPolicies;

@Service
public class PurchasedPolicyService {
	
	@Autowired
	private PurchasedPolicyRepository purchasedpolicyrepository;
	
	public ArrayList<PurchasedPolicies> allPurchasedPolicies() {
		
		ArrayList<PurchasedPolicies> purchasedPolicies = (ArrayList<PurchasedPolicies>) purchasedpolicyrepository.findAll();
		return purchasedPolicies;
	}
	
	public PurchasedPolicies getPurchasedPolicyById(Integer policyId) {
	
		Optional<PurchasedPolicies> purchasedPolicies = purchasedpolicyrepository.findById(policyId);
		return purchasedPolicies.get();
	}
	
    public PurchasedPolicies createPurchasedPolicy(PurchasedPolicies purchasedPolicy) {
    	return (PurchasedPolicies) purchasedpolicyrepository.save(purchasedPolicy);
    	
    }
    public PurchasedPolicies updatePurchasedPolicy(PurchasedPolicies purchasedPolicy) {
    	
    	Optional<PurchasedPolicies> optional= purchasedpolicyrepository.findById(purchasedPolicy.getProductId());
    	PurchasedPolicies tempPolicy= optional.get();
    	
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
}
