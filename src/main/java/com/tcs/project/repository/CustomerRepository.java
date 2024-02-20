package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
