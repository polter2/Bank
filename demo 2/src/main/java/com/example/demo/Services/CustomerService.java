package com.example.demo.Services;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Customer;
import com.example.demo.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        customerRepository.createCustomer(customer);
    }
    public List<Customer> getAll() {
        return customerRepository.getAll();
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteCustomer(id);
    }

    public void updateCustomer(Long id, Customer updatedCustomer) {
        customerRepository.updateCustomer(id, updatedCustomer);
    }

}
