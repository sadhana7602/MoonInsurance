package com.tcs.project.resource;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@Entity
public class Claims {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int claimId;
	protected int policyId;
	protected String policyNo;
	protected int  productId;
	protected int customerId;
	protected String claimNo;
	protected Date claimEntryDate;
	protected String causeOfLoss;
	protected Double claimAmount;
	
}