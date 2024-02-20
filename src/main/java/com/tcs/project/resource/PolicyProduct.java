package com.tcs.project.resource;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@Entity
public class PolicyProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer policyId;
	@NotNull
	private String type;
	@NotNull
	private Double coverageAmount;
	@NotNull
	private Double premium;
	@NotNull
	private int tenure;
	
	
	
	
}
