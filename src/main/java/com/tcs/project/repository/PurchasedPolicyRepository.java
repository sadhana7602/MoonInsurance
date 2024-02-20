package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.PurchasedPolicies;

@Repository
public interface PurchasedPolicyRepository extends JpaRepository<PurchasedPolicies, Integer> {

}
