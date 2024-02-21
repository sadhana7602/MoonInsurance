package com.tcs.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.HealthInsuranceRepository;
import com.tcs.project.resource.HealthInsurance;

import java.util.List;
import java.util.Optional;
 
@Service
public class HealthInsuranceService {
 
    @Autowired
    private HealthInsuranceRepository healthInsuranceRepository;
 
    public HealthInsurance createHealthInsurance(HealthInsurance healthInsurance) {
        return healthInsuranceRepository.save(healthInsurance);
    }
 
    public List<HealthInsurance> getAllHealthInsurances() {
        return healthInsuranceRepository.findAll();
    }
 
    public Optional<HealthInsurance> getHealthInsuranceById(int id) {
        return healthInsuranceRepository.findById(id);
    }
 
    public HealthInsurance updateHealthInsurance(HealthInsurance updatedHealthInsurance) {
    	
    	Optional<HealthInsurance> optional = healthInsuranceRepository.findById(updatedHealthInsurance.getRecordId());
    	HealthInsurance tempHealthInsurance = optional.get();
    	tempHealthInsurance.setCustomerId(updatedHealthInsurance.getCustomerId());
    	tempHealthInsurance.setPolicyNo(updatedHealthInsurance.getPolicyNo());
    	tempHealthInsurance.setGender(updatedHealthInsurance.getGender());
    	tempHealthInsurance.setAge(updatedHealthInsurance.getAge());

		return (HealthInsurance) healthInsuranceRepository.save(tempHealthInsurance);
    }
 
    public boolean deleteHealthInsurance(Integer id) {
        healthInsuranceRepository.deleteById(id);
        return true;
    }
}


