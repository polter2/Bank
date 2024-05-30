package com.example.demo.Entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String sex;
    private String nationality;
    private Date dateOfBirth;
    private String cardNumber;
    private Date cardDateOfIssue;
    private Date cardDateOfExpiry;

}
