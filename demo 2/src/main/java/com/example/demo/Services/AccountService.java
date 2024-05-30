package com.example.demo.Services;

import com.example.demo.Entity.Account;
import com.example.demo.Repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public void createAccount(Account account) {
        accountRepository.createAccount(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteAccount(id);
    }

    public void updateAccount(Long id, Account updatedAccount) {
        accountRepository.updateAccount(id, updatedAccount);
    }
}
