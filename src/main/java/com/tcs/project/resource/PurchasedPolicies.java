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
public class PurchasedPolicies {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int policyNo;
	protected int customerId;
	protected int productId;
	protected Date effectiveDate;
	protected Date expiryDate;
	protected String nominee;
	
}
