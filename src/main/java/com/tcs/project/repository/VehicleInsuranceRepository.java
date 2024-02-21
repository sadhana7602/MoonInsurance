package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.project.resource.VehicleInsurance;

public interface VehicleInsuranceRepository extends JpaRepository<VehicleInsurance, Integer> {
}
