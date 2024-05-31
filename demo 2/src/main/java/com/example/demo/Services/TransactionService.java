package com.example.demo.Services;

import com.example.demo.Controllers.TransactionController;
import com.example.demo.Entity.Account;
import com.example.demo.Entity.Transaction;
import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void transferCredits(String fromIban, String toIban, BigDecimal amount, String message) {
        Account fromAccount = accountRepository.findByIban(fromIban);
        Account toAccount = accountRepository.findByIban(toIban);

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        logger.info("transfer is in progress");
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.updateAccount(fromAccount.getId(), fromAccount);
        accountRepository.updateAccount(toAccount.getId(), toAccount);
        LocalDate dateL = LocalDate.now();
        Date dateNow = Date.valueOf(dateL);
        Transaction transaction = new Transaction();
        transaction.setDate_transaction(dateNow);
        transaction.setAmount(amount);
        transaction.setDebtor_iban(fromIban);
        transaction.setCreditor_iban(toIban);
        transaction.setMessage(message);
        transactionRepository.save(transaction);
        logger.info("transfer successful");
    }

    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    public List<Transaction> searchTransactions(String iban, Double amount, String message) {
        return transactionRepository.searchTransactions(iban, amount, message);
    }
}
