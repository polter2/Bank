package com.example.demo.Repository;

import com.example.demo.Entity.Account;
import com.example.demo.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Account> getAllAccounts() {
        logger.info("getting all accounts");
        String sql = "SELECT * FROM account";
        logger.info("create successful");
        return jdbcTemplate.query(sql, this::mapRowToAccount);
    }

    public void createAccount(Account account) {
        logger.info("creating account");
        String sql = "INSERT INTO account (iban, currency, balance, customer_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, account.getIban(), account.getCurrency(), account.getBalance(), account.getCustomer_id());
        logger.info("create successful");
    }

    public void deleteAccount(Long id) {
        logger.info("deletion account");
        String sql = "DELETE FROM account WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("delete successful");
    }

    public void updateAccount(Long accountId, Account updatedAccount) {
        Account existingAccount = getAccountById(accountId);
        if (existingAccount == null) {
            throw new IllegalArgumentException("Account not found with id: " + accountId);
        }
        logger.info("updating data");
        String sql = "UPDATE account SET iban = ?, currency = ?, balance = ?, customer_id = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                updatedAccount.getIban() != null ? updatedAccount.getIban() : existingAccount.getIban(),
                updatedAccount.getCurrency() != null ? updatedAccount.getCurrency() : existingAccount.getCurrency(),
                updatedAccount.getBalance() != null ? updatedAccount.getBalance() : existingAccount.getBalance(),
                updatedAccount.getCustomer_id() != null ? updatedAccount.getCustomer_id() : existingAccount.getCustomer_id(),
                accountId);
        logger.info("update successful");
    }

    private Account mapRowToAccount(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        try {
            account.setId(rs.getLong("id"));
            account.setIban(rs.getString("iban"));
            account.setCurrency(rs.getString("currency"));
            account.setBalance(rs.getBigDecimal("balance"));
            account.setCustomer_id(rs.getInt("customer_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }
    public Account getAccountById(Long accountId) {
        logger.info("search account by id");
        String sql = "SELECT * FROM account WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, this::mapRowToAccount);
    }

    public Account findByIban(String accountIban) {
        logger.info("search account by iban");
        String sql = "SELECT * FROM account WHERE iban = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{accountIban}, this::mapRowToAccount);
    }
}
