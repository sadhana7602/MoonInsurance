package com.tcs.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.resource.PolicyProduct;

@Service
public class PolicyProductService {
	
	@Autowired
	PolicyProductRepository policyproductrepository;

	
	public ArrayList<PolicyProduct> allPolicyProductById(Integer policyId) {
		
		ArrayList<PolicyProduct> policies = (ArrayList<PolicyProduct>) policyproductrepository.findAll();
		return   policies;
	}
	
	public PolicyProduct getPolicyProductById(Integer policyId) {
	
		Optional<PolicyProduct> policy = policyproductrepository.findById(policyId);
		return  policy.get();
	}
	
    public PolicyProduct createPolicyProduct(PolicyProduct policyproduct) {
    	return (PolicyProduct) policyproductrepository.save(policyproduct);
    	
    }
    public PolicyProduct updatePolicyProduct(PolicyProduct policyproduct) {
    	
    	Optional<PolicyProduct> optional= policyproductrepository.findById(policyproduct.getPolicyId());
    	PolicyProduct tempPolicy= optional.get();
    	tempPolicy.setType(policyproduct.getType());
    	tempPolicy.setCoverageAmount(policyproduct.getCoverageAmount());
    	tempPolicy.setPremium(policyproduct.getPremium());
    	tempPolicy.setTenure(policyproduct.getTenure());
    	
    	
    	return (PolicyProduct) policyproductrepository.save(tempPolicy);
    }
	
    public boolean deletePolicyProduct(Integer policyId) {
		
    	policyproductrepository.deleteById(policyId);
    	return true;
	}
    
	
	
	

}
