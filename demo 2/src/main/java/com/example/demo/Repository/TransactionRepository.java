package com.example.demo.Repository;

import com.example.demo.Controllers.TransactionController;
import com.example.demo.Entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Transaction transaction) {
        logger.info("updating data");
        String sql = "INSERT INTO transaction_transfer (date_transaction, amount, debtor_iban, creditor_iban, message) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                transaction.getDate_transaction(),
                transaction.getAmount(),
                transaction.getDebtor_iban(),
                transaction.getCreditor_iban(),
                transaction.getMessage());
        logger.info("update successful");
    }

    public List<Transaction> findAll() {
        logger.info("getting all transactions");
        String sql = "SELECT * FROM transaction_transfer ORDER BY date_transaction ASC";
        logger.info("getting successful");
        return jdbcTemplate.query(sql, new TransactionRowMapper());
    }

    public List<Transaction> searchTransactions(String iban, Double amount, String message) {
        logger.info("getting all transactions by param");
        String sql = "SELECT * FROM transaction_transfer WHERE debtor_iban = ? OR creditor_iban = ? OR amount = ? OR message LIKE ?";
        logger.info("getting successful");
        return jdbcTemplate.query(sql, new Object[]{iban, iban, amount, message}, new TransactionRowMapper());
    }

    private static class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction();
            transaction.setId(rs.getLong("id"));
            transaction.setDate_transaction(rs.getDate("date_transaction"));
            transaction.setAmount(rs.getBigDecimal("amount"));
            transaction.setDebtor_iban(rs.getString("debtor_iban"));
            transaction.setCreditor_iban(rs.getString("creditor_iban"));
            transaction.setMessage(rs.getString("message"));
            return transaction;
        }
    }
}
