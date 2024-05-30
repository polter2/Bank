package com.example.demo.Controllers;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Customer;
import com.example.demo.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get")
    public List<Customer> getAllAccounts() {
        logger.info("request to get all accounts");
        return customerService.getAll();
    }

    @PostMapping("/create")
    public void createCustomer(@RequestBody Customer customer) {
        logger.info("request to create new customer");
        customerService.createCustomer(customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        logger.info("request to delete customer");
        customerService.deleteCustomer(id);
    }

    @PutMapping("/update/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        logger.info("request to update customer");
        customerService.updateCustomer(id, updatedCustomer);
    }
}
