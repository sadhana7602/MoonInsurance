package com.tcs.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.Claims;
import com.tcs.project.resource.PolicyProduct;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Integer> {
	public Claims findByPolicyId(int policyId); 
	public List<Claims> findAllByCustomerId(int customerId);

}