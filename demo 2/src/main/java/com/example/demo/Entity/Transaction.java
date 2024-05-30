package com.example.demo.Entity;

import lombok.Data;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;

@Data
public class Transaction {
    private Long id;
    private Date date_transaction;
    private BigDecimal amount;
    private String debtor_iban;
    private String creditor_iban;
    private String message;
}
