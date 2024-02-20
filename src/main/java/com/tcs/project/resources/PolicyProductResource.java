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

import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.services.PolicyProductService;

@RestController
@RequestMapping("/policyproduct")
public class PolicyProductResource {
	
	@Autowired
	private PolicyProductService policyproductservice;
	
	@GetMapping("/policieproducts")
	public ResponseEntity<ArrayList<PolicyProduct>> allPolicyProductHandler(@PathVariable("policyId") Integer policyId){
		return new ResponseEntity<ArrayList<PolicyProduct>> (policyproductservice.allPolicyProductById(policyId),
				HttpStatus.OK);
		
	}
	
	
	@GetMapping("/policyproductbyid/{pn}")
	public ResponseEntity<PolicyProduct> getPolicyProductHandler(@PathVariable("pn") Integer policyId){
		return new ResponseEntity<PolicyProduct> (policyproductservice.getPolicyProductById(policyId),
				HttpStatus.OK);
		
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/addpolicyproduct")
	public ResponseEntity<PolicyProduct> createPolicyProductHandler(@RequestBody PolicyProduct policyproduct){
		return new ResponseEntity<PolicyProduct> (policyproductservice.createPolicyProduct(policyproduct),
				HttpStatus.CREATED);
		
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/updatepolicyproduct")
	public ResponseEntity<PolicyProduct> updatePolicyProductHandler(@RequestBody PolicyProduct policyproduct){
		return new ResponseEntity<PolicyProduct> (policyproductservice.updatePolicyProduct(policyproduct),
				HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deletepolicyproduct/{pn}")
	public boolean deletePolicyProductHandler(@PathVariable("pn") Integer policyId){
		return policyproductservice.deletePolicyProduct(policyId);
		
	}
	
}
