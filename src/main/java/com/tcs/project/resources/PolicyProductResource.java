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
	
	@GetMapping("/all")
	public ResponseEntity<ArrayList<PolicyProduct>> allPolicyProduct(){
		return new ResponseEntity<ArrayList<PolicyProduct>> (policyproductservice.allPolicyProductById(),
				HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<PolicyProduct> getPolicyProduct(@PathVariable("id") Integer id){
		return new ResponseEntity<PolicyProduct> (policyproductservice.getPolicyProductById(id),
				HttpStatus.OK);	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/add")
	public ResponseEntity<PolicyProduct> createPolicyProduct(@RequestBody PolicyProduct policyproduct){
		return new ResponseEntity<PolicyProduct> (policyproductservice.createPolicyProduct(policyproduct),
				HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value = "/update")
	public ResponseEntity<PolicyProduct> updatePolicyProduct(@RequestBody PolicyProduct policyproduct){
		return new ResponseEntity<PolicyProduct> (policyproductservice.updatePolicyProduct(policyproduct),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deletePolicyProduct(@PathVariable("id") Integer id){
		return policyproductservice.deletePolicyProduct(id);
	}
	
}
