package com.tcs.project.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.project.resource.HealthInsurance;
import com.tcs.project.services.HealthInsuranceService;

@RestController
@RequestMapping("/health-insurance")
public class HealthInsuranceResource {
 
    @Autowired
    private HealthInsuranceService healthInsuranceService;
    
    @GetMapping("/all")
    public ResponseEntity<List<HealthInsurance>> getAllHealthInsurances() {
        List<HealthInsurance> healthInsurances = healthInsuranceService.getAllHealthInsurances();
        return ResponseEntity.ok(healthInsurances);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<HealthInsurance> getHealthInsuranceById(@PathVariable int id) {
        Optional<HealthInsurance> healthInsuranceOptional = healthInsuranceService.getHealthInsuranceById(id);
        return healthInsuranceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/add")
    public ResponseEntity<HealthInsurance> createHealthInsurance(@RequestBody HealthInsurance healthInsurance) {
        HealthInsurance createdHealthInsurance = healthInsuranceService.createHealthInsurance(healthInsurance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHealthInsurance);
    }
 
    @PutMapping("/update")
    public ResponseEntity<HealthInsurance> updateHealthInsurance(@RequestBody HealthInsurance updatedHealthInsurance) {
        HealthInsurance updated = healthInsuranceService.updateHealthInsurance(updatedHealthInsurance);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
 
    @DeleteMapping("/delete/{id}")
    public boolean deleteHealthInsurance(@PathVariable int id) {
        return healthInsuranceService.deleteHealthInsurance(id);
    }
    
    @GetMapping("/adminall")
	public ResponseEntity<ArrayList<Object[]>> allAdminPurchasedPolicies(){
		return new ResponseEntity<ArrayList<Object[]>> (healthInsuranceService.allAdminPurchasedPolicies() ,HttpStatus.OK);
	}

}
