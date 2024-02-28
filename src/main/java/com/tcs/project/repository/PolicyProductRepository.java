package com.tcs.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.PolicyProduct;

@Repository
public interface PolicyProductRepository extends JpaRepository<PolicyProduct, Integer>  {
     public List<PolicyProduct> findByProductName(String productName);
}
