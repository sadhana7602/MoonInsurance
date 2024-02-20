package com.tcs.project.resource;

import java.util.Date;

import jakarta.persistence.Entity;
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
	protected String policyNo;
	protected int customerId;
	protected int productId;
	protected Date effectiveDate;
	protected Date expiryDate;
	protected String nominee;
	
}
