package com.tcs.project.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Customerid;
    private String Name;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    
    //private List<PolicyList> policies = new ArrayList<>();    
    //private List<Claims> claims = new ArrayList<>();

}



