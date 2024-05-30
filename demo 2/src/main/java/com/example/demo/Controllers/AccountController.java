package com.example.demo.Controllers;

import com.example.demo.Entity.Account;
import com.example.demo.Services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/get")
    public List<Account> getAllAccounts() {
        logger.info("request to get all accounts");
        return accountService.getAllAccounts();
    }

    @PostMapping("/create")
    public void createAccount(@RequestBody Account account) {
        logger.info("request to create new account");
        accountService.createAccount(account);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id) {
        logger.info("request to delete account");
        accountService.deleteAccount(id);
    }

    @PutMapping("/update/{id}")
    public void updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        logger.info("request to update account");
        accountService.updateAccount(id, updatedAccount);
    }
}
