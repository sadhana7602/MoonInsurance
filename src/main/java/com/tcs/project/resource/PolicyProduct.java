package com.tcs.project.resource;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@Entity
public class PolicyProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer productId;
	@NotNull
	protected String productCode;
	@NotNull
	protected String productName;
	@NotNull
	protected String productTier;
	@NotNull
	protected Double coverageAmount;
	@NotNull
	protected String coverageDescription;
	@NotNull
	protected Double productPremium;
	@NotNull
	protected int tenure;
}
