package com.tcs.project.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.project.services.ClaimsService;
import com.tcs.project.resource.Claims;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/claims")
public class ClaimsResource {
	
	@Autowired
	private ClaimsService claimservice;
	
	@GetMapping("/all")
	public ArrayList<Claims> getAllClaims() {
		return claimservice.allClaims();	
	}
	@GetMapping("/allcustomerclaim")
	public ArrayList<Claims> getAllCustomerClaims(@RequestBody int cid) {
		return claimservice.allCustomerClaims(cid);	
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Claims> getPurchasedPolicies(@PathVariable("id") Integer id){
		return new ResponseEntity<Claims> (claimservice.getClaimById(id) , HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			
			value = "/add")
	public ResponseEntity<Boolean> createPurchasedPolicy(@RequestBody Claims claim){
		return new ResponseEntity<Boolean> (claimservice.createClaim(claim), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/update")
	public ResponseEntity<Claims> updatePolicyProductHandler(@RequestBody Claims claim){
		return new ResponseEntity<Claims> (claimservice.updateClaim(claim), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deleteClaim(@PathVariable("id") Integer id){
		return claimservice.deleteClaim(id);
	}
	@PostMapping("/adminvalidate")
	public boolean adminvalidationHandler(@RequestBody int pid){
		return claimservice.adminvalidation(pid);
	}
	
}