package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.HomeInsurance;

@Repository
public interface HomeInsuranceRepository extends JpaRepository<HomeInsurance, Integer> {

}
