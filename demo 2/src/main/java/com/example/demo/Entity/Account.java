package com.example.demo.Entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Account {
    private Long id;
    private String iban;
    private String currency;
    private BigDecimal balance;
    private Integer customer_id;
}
