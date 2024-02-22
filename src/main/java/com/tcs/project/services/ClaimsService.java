package com.tcs.project.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.ClaimsRepository;
import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.HealthInsuranceRepository;
import com.tcs.project.repository.HomeInsuranceRepository;
import com.tcs.project.repository.PolicyProductRepository;
import com.tcs.project.repository.VehicleInsuranceRepository;
import com.tcs.project.resource.Claims;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.HealthInsurance;
import com.tcs.project.resource.HomeInsurance;
import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.resource.VehicleInsurance;

@Service
public class ClaimsService {
	
	@Autowired
	private ClaimsRepository claimsrepository;
	@Autowired
	PolicyProductRepository policyproductrepository;
	@Autowired
	CustomerRepository customerrepository;
	@Autowired
	HealthInsuranceRepository healthinsurancerepository;
	@Autowired
	HomeInsuranceRepository homeinsurancerepository;
	@Autowired
	VehicleInsuranceRepository vehicleinsurancerepository;
	
	public ArrayList<Claims> allClaims() {
		ArrayList<Claims> claims = (ArrayList<Claims>) claimsrepository.findAll();
		return claims;
	}
	
	public Claims getClaimById(Integer id) {
		Optional<Claims> claim = claimsrepository.findById(id);
		return  claim.get();
	}
	
    public Claims createClaim(Claims claim) {
    	return (Claims) claimsrepository.save(claim);
    }
    
    public Claims updateClaim(Claims claim) {
    	Optional<Claims> optional= claimsrepository.findById(claim.getRecordId());
    	Claims tempClaim= optional.get();
    	tempClaim.setClaimNo(claim.getClaimNo());
    	tempClaim.setCustomerId(claim.getCustomerId());
    	tempClaim.setProductId(claim.getProductId());
    	tempClaim.setClaimEntryDate(claim.getClaimEntryDate());
    	tempClaim.setCauseOfLoss(claim.getCauseOfLoss());
    	tempClaim.setClaimAmount(claim.getClaimAmount());
    	return (Claims) claimsrepository.save(tempClaim);
    }
	
    public boolean deleteClaim(Integer id) {
    	claimsrepository.deleteById(id);
    	return true;
	}
    
    public boolean adminvalidation(String pno){
    	String pid = pno.substring(7); // Extracting last 3 digits
        int policyId = Integer.parseInt(pid); // Converting to integer to remove leading zeros
        
        Claims claim= claimsrepository.getById(policyId);
        
        PolicyProduct product= policyproductrepository.getById(claim.getProductId());
        
        if(product.getProductName()=="Health") {
        	HealthInsurance hi = healthinsurancerepository.getById(policyId);
        	
        	if(hi.getAge()>18 && hi.getAge()<60) {
        		if(product.getCoverageDescription().contains(claim.getCauseOfLoss())) {
        			if(claim.getClaimAmount()<= hi.getCoverageBalance()) {
        				if(claim.getClaimAmount()<= product.getCoverageAmount()) {
        					return true;
        				}
        			}
        		}
        	}
        	
        }
        if(product.getProductName()=="Vehicle") {
        VehicleInsurance hi = vehicleinsurancerepository.getById(policyId);
        
        LocalDate localDate = hi.getFcDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        	if(localDate.isAfter(currentDate)) {
        		if(product.getCoverageDescription().contains(claim.getCauseOfLoss())) {
        			if(claim.getClaimAmount()<= hi.getCoverageBalance()) {
        				if(claim.getClaimAmount()<= product.getCoverageAmount()) {
        					return true;
        				}
        			}
        		}
        	}
        	
        }
        if(product.getProductName()=="Home") {
            HomeInsurance hi = homeinsurancerepository.getById(policyId);
            
            
            	if(hi.getAssetValue()>1000000) {
            		if(product.getCoverageDescription().contains(claim.getCauseOfLoss())) {
            			if(claim.getClaimAmount()<= hi.getCoverageBalance()) {
            				if(claim.getClaimAmount()<= product.getCoverageAmount()) {
            					return true;
            				}
            			}
            		}
            	}
            	
            }
        
        
    	return false;
    }
}