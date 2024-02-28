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
    	//String pid = pno.substring(7); // Extracting last 3 digits
        //int policyId = Integer.parseInt(pid); // Converting to integer to remove leading zeros
        //System.out.println(policyId);
        
        Claims claimRecord=claimsrepository.findByPolicyId(policyId);
        
        
        System.out.println(claimRecord);
        PolicyProduct product= policyproductrepository.getById(claimRecord.getProductId());
        System.out.println(product);
        
        if(product.getProductName().equals("Health")) {
        	HealthInsurance hi = healthinsurancerepository.getById(policyId);
        	
        	if(hi.getAge()>18 && hi.getAge()<60) {
        		System.out.println(hi.getAge());
        		if(product.getCoverageDescription().contains(claimRecord.getCauseOfLoss())) {
        			System.out.println(product.getCoverageDescription());
        			if(claimRecord.getClaimAmount()<= hi.getCoverageBalance()) {
        				System.out.println(claimRecord.getClaimAmount());
        				if(claimRecord.getClaimAmount()<= product.getCoverageAmount()) {
        					System.out.println(claimRecord.getClaimAmount());
        					claimRecord.setStatus("APPROVED");
        					claimsrepository.save(claimRecord);
        					return true;
        				}
        			}
        		}
        	}
        	
        }
        else if(product.getProductName().equals("Auto")) {
        VehicleInsurance hi = vehicleinsurancerepository.getById(policyId);
        
        LocalDate localDate = hi.getFcDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        	if(localDate.isAfter(currentDate)) {
        		System.out.println(localDate.isAfter(currentDate));
        		if(product.getCoverageDescription().contains(claimRecord.getCauseOfLoss())) {
        			System.out.println(product.getCoverageDescription());
        			if(claimRecord.getClaimAmount()<= hi.getCoverageBalance()) {
        				System.out.println(claimRecord.getClaimAmount());
        				if(claimRecord.getClaimAmount()<= product.getCoverageAmount()) {
        					claimRecord.setStatus("APPROVED");
        					claimsrepository.save(claimRecord);
        					return true;
        				}
        			}
        		}
        	}
        	
        }
        else if(product.getProductName().equals("Home")) {
            HomeInsurance hi = homeinsurancerepository.getById(policyId);
            
            System.out.println(hi);
            	if(hi.getAssetValue()>10000) {
            		System.out.println(hi.getAssetValue());
            		if(product.getCoverageDescription().contains(claimRecord.getCauseOfLoss())) {
            			System.out.println(product.getCoverageDescription() + " " + claimRecord.getCauseOfLoss());
            			if(claimRecord.getClaimAmount()<= hi.getCoverageBalance()) {
            				System.out.println(claimRecord.getClaimAmount() + " " + hi.getCoverageBalance() + " " + product.getCoverageAmount());
            				if(claimRecord.getClaimAmount()<= product.getCoverageAmount()) {
            					claimRecord.setStatus("APPROVED");
            					claimsrepository.save(claimRecord);
            					
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