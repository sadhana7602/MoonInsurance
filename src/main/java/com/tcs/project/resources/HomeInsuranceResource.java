package com.tcs.project.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.project.resource.HomeDto;
import com.tcs.project.resource.HomeInsurance;
import com.tcs.project.resource.PolicyProduct;
import com.tcs.project.services.HomeInsuranceService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/home-insurance")
public class HomeInsuranceResource {
	@Autowired
    private HomeInsuranceService homeInsuranceService;
    
    @GetMapping("/all")
    public ResponseEntity<List<HomeInsurance>> getAllHomeInsurances() {
        List<HomeInsurance> homeInsurances = homeInsuranceService.getAllHomeInsurances();
        return ResponseEntity.ok(homeInsurances);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<HomeInsurance> getHomeInsuranceById(@PathVariable int id) {
        Optional<HomeInsurance> homeInsuranceOptional = homeInsuranceService.getHomeInsuranceById(id);
        return homeInsuranceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/add")
    public ResponseEntity<Boolean> createHomeInsurance(@RequestBody HomeDto homedto) {
        boolean createdHomeInsurance = homeInsuranceService.createHomeInsurance(homedto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHomeInsurance);
    }
 
    @PutMapping("/update")
    public ResponseEntity<HomeInsurance> updateHomeInsurance(@RequestBody HomeInsurance updatedHomeInsurance) {
    	HomeInsurance updated = homeInsuranceService.updateHomeInsurance(updatedHomeInsurance);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}")
    public boolean deleteHomeInsurance(@PathVariable int id) {
        return homeInsuranceService.deleteHomeInsurance(id);
    }
    
    
    @GetMapping("/homeall")
	public ResponseEntity<List<PolicyProduct>> allHealthPolicies(){
		return new ResponseEntity<List<PolicyProduct>> (homeInsuranceService.allHomePolicies() ,HttpStatus.OK);
	}
}

