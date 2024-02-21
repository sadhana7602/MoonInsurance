package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.HealthInsurance;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Integer> {

}
