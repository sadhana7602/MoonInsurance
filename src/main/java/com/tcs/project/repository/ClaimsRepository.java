package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.project.resource.Claims;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Integer> {

}