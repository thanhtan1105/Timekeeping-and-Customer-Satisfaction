package com.service.serviceImplement;

import com.entity.Customer;
import com.repository.CustomerRepo;
import com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lethanhtan on 9/2/16.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepo repository) {
        this.repository = repository;
    }

    public Customer save(String firstname, String lastname) {
        Customer customer = new Customer(firstname, lastname, true);
        return repository.save(customer);
    }

    public List<Customer> findByLastName(String lastname) {
        return repository.findByLastName(lastname);
    }

    public boolean deleteById(Long id) {
        try {
            repository.delete(id);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }


}
