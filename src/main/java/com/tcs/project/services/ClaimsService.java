package com.tcs.project.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.ClaimsRepository;
import com.tcs.project.resource.Claims;

@Service
public class ClaimsService {
	
	@Autowired
	private ClaimsRepository claimsrepository;
	
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
    	tempClaim.setClaimEntryDate(claim.getClaimEntryDate());
    	tempClaim.setCauseOfLoss(claim.getCauseOfLoss());
    	tempClaim.setClaimAmount(claim.getClaimAmount());
    	return (Claims) claimsrepository.save(tempClaim);
    }
	
    public boolean deleteClaim(Integer id) {
    	claimsrepository.deleteById(id);
    	return true;
	}
    
}