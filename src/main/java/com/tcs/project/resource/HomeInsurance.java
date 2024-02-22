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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HomeInsurance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyId;
    private int productId;
    private int customerId;
    private String policyNo;
    private char gender;
    private int age;
    private Date effectiveDate;
    private Date expiryDate;
    private String nominee;
    private Double coverageBalance;
    private int renewal;
    private String homeType;
    private Double builtArea;
    private Double assetValue;
}
