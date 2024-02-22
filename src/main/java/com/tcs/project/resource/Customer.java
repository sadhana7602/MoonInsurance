package com.tcs.project.resource;

import java.time.LocalDate;
import java.util.ArrayList;

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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Customerid;
    private String Name;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    
    //private ArrayList<PurchasedPolicies> policies = new ArrayList<>();    
    //private ArrayList<Claims> claims = new ArrayList<>();

}



