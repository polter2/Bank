package com.example.demo.Controllers;

import com.example.demo.Entity.Transaction;
import com.example.demo.Services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/history")
    public List<Transaction> getHistory(){
        logger.info("request to get all transactions");
        return transactionService.getTransactionHistory();
    }
    @GetMapping("/search")
    public List<Transaction> getTransaction(@RequestParam(required = false) String iban, @RequestParam(required = false) Double amount, @RequestParam(required = false) String message){
        logger.info("request to get transactions by param");
        return transactionService.searchTransactions(iban, amount, message);
    }
    @PostMapping("/transfer")
    public String transferCreditsApi(@RequestParam String fromIban, @RequestParam String toIban, @RequestParam BigDecimal amount, @RequestParam String message) {
        logger.info("request to transfer");
        transactionService.transferCredits(fromIban, toIban, amount, message);
        return "Transfer completed";
    }
}
