package com.tcs.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.project.resource.Query;

public interface QueryRepository extends JpaRepository<Query, Integer> {
}

