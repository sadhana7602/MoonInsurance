package com.tcs.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.VehicleInsuranceRepository;
import com.tcs.project.resource.VehicleInsurance;

@Service
public class VehicleInsuranceService {
 
    @Autowired
    private VehicleInsuranceRepository vehicleInsuranceRepository;
 
    public VehicleInsurance createVehicleInsurance(VehicleInsurance vehicleInsurance) {
        return vehicleInsuranceRepository.save(vehicleInsurance);
    }
 
    public Optional<VehicleInsurance> getVehicleInsuranceById(int id) {
        return vehicleInsuranceRepository.findById(id);
    }
 
    public VehicleInsurance updateVehicleInsurance(VehicleInsurance updatedVehicleInsurance) {      
        
        Optional<VehicleInsurance> optional = vehicleInsuranceRepository.findById(updatedVehicleInsurance.getRecordId());
        VehicleInsurance tempVehicleInsurance = optional.get();
    	tempVehicleInsurance.setCustomerId(updatedVehicleInsurance.getCustomerId());
    	tempVehicleInsurance.setPolicyNo(updatedVehicleInsurance.getPolicyNo());
    	tempVehicleInsurance.setPurchaseDate(updatedVehicleInsurance.getPurchaseDate());
    	tempVehicleInsurance.setMake(updatedVehicleInsurance.getMake());
    	tempVehicleInsurance.setModel(updatedVehicleInsurance.getModel());
    	tempVehicleInsurance.setFcDate(updatedVehicleInsurance.getFcDate());
    	
		return (VehicleInsurance) vehicleInsuranceRepository.save(tempVehicleInsurance);
    }
 
    public boolean deleteVehicleInsurance(int id) {
        vehicleInsuranceRepository.deleteById(id);
        return true;
    }
 
    public List<VehicleInsurance> getAllVehicleInsurances() {
        return vehicleInsuranceRepository.findAll();
    }
}
