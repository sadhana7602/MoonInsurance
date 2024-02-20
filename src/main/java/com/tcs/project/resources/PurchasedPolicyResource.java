package com.tcs.project.resources;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.project.resource.PurchasedPolicies;
import com.tcs.project.services.PurchasedPolicyService;

@RestController
@RequestMapping("/purchasedpolicy")
public class PurchasedPolicyResource {
	
	@Autowired
	private PurchasedPolicyService purchasedpolicyservice;
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<PurchasedPolicies>> allPurchasedPolicies(){
		return new ResponseEntity<ArrayList<PurchasedPolicies>> (purchasedpolicyservice.allPurchasedPolicies() ,HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<PurchasedPolicies> getPurchasedPolicies(@PathVariable("id") Integer policyId){
		return new ResponseEntity<PurchasedPolicies> (purchasedpolicyservice.getPurchasedPolicyById(policyId) , HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/add")
	public ResponseEntity<PurchasedPolicies> createPurchasedPolicy(@RequestBody PurchasedPolicies purchasedPolicy){
		return new ResponseEntity<PurchasedPolicies> (purchasedpolicyservice.createPurchasedPolicy(purchasedPolicy), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/update")
	public ResponseEntity<PurchasedPolicies> updatePolicyProductHandler(@RequestBody PurchasedPolicies purchasedPolicy){
		return new ResponseEntity<PurchasedPolicies> (purchasedpolicyservice.updatePurchasedPolicy(purchasedPolicy), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deletePolicyProductHandler(@PathVariable("id") Integer policyId){
		return purchasedpolicyservice.deletePolicyProduct(policyId);
	}
}
