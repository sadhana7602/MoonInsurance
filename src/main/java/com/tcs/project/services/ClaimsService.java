package com.tcs.project.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
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
	public ArrayList<Claims> allCustomerClaims(int cid) {
		ArrayList<Claims> claims = (ArrayList<Claims>) claimsrepository.findAllByCustomerId(cid);
		return claims;
	}
	
	public Claims getClaimById(Integer id) {
		Optional<Claims> claim = claimsrepository.findById(id);
		return  claim.get();
	}
	
    public boolean createClaim(Claims claim) {
    	claimsrepository.save(claim);
    	return true;
    }
    
    public Claims updateClaim(Claims claim) {
    	Optional<Claims> optional= claimsrepository.findById(claim.getClaimId());
    	Claims tempClaim= optional.get();
    	tempClaim.setPolicyId(claim.getPolicyId());
    	
    	tempClaim.setCustomerId(claim.getCustomerId());
    	tempClaim.setProductId(claim.getProductId());
    	tempClaim.setClaimEntryDate(claim.getClaimEntryDate());
    	tempClaim.setCauseOfLoss(claim.getCauseOfLoss());
    	tempClaim.setClaimAmount(claim.getClaimAmount());
    	tempClaim.setStatus(claim.getStatus());
    	return (Claims) claimsrepository.save(tempClaim);
    }
	
    public boolean deleteClaim(Integer id) {
    	claimsrepository.deleteById(id);
    	return true;
	}
    
    public boolean adminvalidation(int policyId){
        
        Claims claimRecord=claimsrepository.findByPolicyId(policyId);
               
        System.out.println(claimRecord);
        PolicyProduct product= policyproductrepository.getById(claimRecord.getProductId());
        System.out.println(product);
        
        if(product.getProductName().equals("Health")) {
        	HealthInsurance hi = healthinsurancerepository.getById(policyId);
        	
        	if(hi.getAge()>18 && hi.getAge()<60) {
        		if(product.getCoverageDescription().contains(claimRecord.getCauseOfLoss())) {
        			if(claimRecord.getClaimAmount()<= hi.getCoverageBalance()) {
        				if(claimRecord.getClaimAmount()<= product.getCoverageAmount()) {
        		
        					claimRecord.setStatus("APPROVED");
        					claimsrepository.save(claimRecord);
        					hi.setCoverageBalance(hi.getCoverageBalance()-claimRecord.getClaimAmount());
        					healthinsurancerepository.save(hi);
        					return true;
        				}
        			}
        		}
        	}
        }
        else if(product.getProductName().equals("Auto")) {
        VehicleInsurance hi = vehicleinsurancerepository.getById(policyId);
        
        LocalDate localDate = hi.getFcDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        LocalDate currentDate = LocalDate.now();
        	if(localDate.isAfter(currentDate)) {
        		
        		if(product.getCoverageDescription().contains(claimRecord.getCauseOfLoss())) {
        		
        			if(claimRecord.getClaimAmount()<= hi.getCoverageBalance()) {
        		
        				if(claimRecord.getClaimAmount()<= product.getCoverageAmount()) {
        					claimRecord.setStatus("APPROVED");
        					claimsrepository.save(claimRecord);
        					hi.setCoverageBalance(hi.getCoverageBalance()-claimRecord.getClaimAmount());
        					vehicleinsurancerepository.save(hi);
        					return true;
        				}
        			}
        		}
        	}
        }
        else if(product.getProductName().equals("Home")) {
            HomeInsurance hi = homeinsurancerepository.getById(policyId);
            
            
            	if(hi.getAssetValue()>10000) {
            
            		if(product.getCoverageDescription().contains(claimRecord.getCauseOfLoss())) {
            
            			if(claimRecord.getClaimAmount()<= hi.getCoverageBalance()) {
            
            				if(claimRecord.getClaimAmount()<= product.getCoverageAmount()) {
            					claimRecord.setStatus("APPROVED");
            					claimsrepository.save(claimRecord);
            					hi.setCoverageBalance(hi.getCoverageBalance()-claimRecord.getClaimAmount());
            					homeinsurancerepository.save(hi);
            					
            					return true;
            				}
            			}
            		}
            	}
            	
            }
        if(claimRecord.getStatus().equalsIgnoreCase("Pending")) {
        	claimRecord.setStatus("Not Approved");
        }
    	return false;
    }
}