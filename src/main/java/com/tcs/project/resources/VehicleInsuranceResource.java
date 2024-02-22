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

import com.tcs.project.resource.VehicleInsurance;
import com.tcs.project.services.VehicleInsuranceService;

@RestController
@RequestMapping("/vehicle-insurance")
public class VehicleInsuranceResource {
 
    @Autowired
    private VehicleInsuranceService vehicleInsuranceService;
    
    @GetMapping("/all")
    public List<VehicleInsurance> getAllVehicleInsurances() {
        return vehicleInsuranceService.getAllVehicleInsurances();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<VehicleInsurance> getVehicleInsuranceById(@PathVariable int id) {
        Optional<VehicleInsurance> vehicleInsurance = vehicleInsuranceService.getVehicleInsuranceById(id);
        return vehicleInsurance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/add")
    public ResponseEntity<VehicleInsurance> createVehicleInsurance(@RequestBody VehicleInsurance vehicleInsurance) {
        VehicleInsurance createdInsurance = vehicleInsuranceService.createVehicleInsurance(vehicleInsurance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInsurance);
    }
    
    @PutMapping("/update")
    public ResponseEntity<VehicleInsurance> updateVehicleInsurance(@RequestBody VehicleInsurance updatedVehicleInsurance) {
        VehicleInsurance updatedInsurance = vehicleInsuranceService.updateVehicleInsurance(updatedVehicleInsurance);
        return ResponseEntity.ok(updatedInsurance);
    }
    
    @DeleteMapping("/delete/{id}")
    public boolean deleteVehicleInsurance(@PathVariable int id) {
        return vehicleInsuranceService.deleteVehicleInsurance(id);
    }
    
    @GetMapping("/adminall")
	public ResponseEntity<ArrayList<Object[]>> allAdminPurchasedPolicies(){
		return new ResponseEntity<ArrayList<Object[]>> (vehicleInsuranceService.allAdminPurchasedPolicies() ,HttpStatus.OK);
	}
    
}
