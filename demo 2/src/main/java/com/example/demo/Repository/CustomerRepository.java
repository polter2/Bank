package com.example.demo.Repository;

import com.example.demo.Entity.Customer;
import com.example.demo.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> getAll() {
        logger.info("getting all customers");
        String sql = "SELECT * FROM customer";
        logger.info("getting successful");
        return jdbcTemplate.query(sql, this::mapRowToCustomer);
    }

    public void createCustomer(Customer customer) {
        logger.info("creating customer");
        String sql = "INSERT INTO customer (name, surname, sex, nationality, date_of_birth, card_number, card_date_of_issue, card_date_of_expiry) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getName(), customer.getSurname(), customer.getSex(), customer.getNationality(), customer.getDateOfBirth(), customer.getCardNumber(), customer.getCardDateOfIssue(), customer.getCardDateOfExpiry());
        logger.info("creating successful");
    }

    public void deleteCustomer(Long id) {
        logger.info("deletion customer");
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
        logger.info("delete successful");
    }

    public void updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(customerId);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer not found with id: " + customerId);
        }
        logger.info("updating data");
        String sql = "UPDATE customer SET name = ?, surname = ?, sex = ?, nationality = ?, date_of_birth = ?, card_number = ?, card_date_of_issue = ?, card_date_of_expiry = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                updatedCustomer.getName() != null ? updatedCustomer.getName() : existingCustomer.getName(),
                updatedCustomer.getSurname() != null ? updatedCustomer.getSurname() : existingCustomer.getSurname(),
                updatedCustomer.getSex() != null ? updatedCustomer.getSex() : existingCustomer.getSex(),
                updatedCustomer.getNationality() != null ? updatedCustomer.getNationality() : existingCustomer.getNationality(),
                updatedCustomer.getDateOfBirth() != null ? updatedCustomer.getDateOfBirth() : existingCustomer.getDateOfBirth(),
                updatedCustomer.getCardNumber() != null ? updatedCustomer.getCardNumber() : existingCustomer.getCardNumber(),
                updatedCustomer.getCardDateOfIssue() != null ? updatedCustomer.getCardDateOfIssue() : existingCustomer.getCardDateOfIssue(),
                updatedCustomer.getCardDateOfExpiry() != null ? updatedCustomer.getCardDateOfExpiry() : existingCustomer.getCardDateOfExpiry(),
                customerId);
        logger.info("update successful");
    }

    private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        try {
            customer.setId(rs.getLong("id"));
            customer.setName(rs.getString("name"));
            customer.setSurname(rs.getString("surname"));
            customer.setSex(rs.getString("sex"));
            customer.setNationality(rs.getString("nationality"));
            customer.setDateOfBirth(rs.getDate("date_of_birth"));
            customer.setCardNumber(rs.getString("card_number"));
            customer.setCardDateOfIssue(rs.getDate("card_date_of_issue"));
            customer.setCardDateOfExpiry(rs.getDate("card_date_of_expiry"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
    public Customer getCustomerById(Long customerId) {
        logger.info("search customer by id");
        String sql = "SELECT * FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{customerId}, this::mapRowToCustomer);
    }
}
