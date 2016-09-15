package com.timelinekeeping.service.serviceImplement;


import com.timelinekeeping.entity.CustomerEntity;
import com.timelinekeeping.repository.CustomerRepo;
import com.timelinekeeping.service.CustomerService;
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

    public CustomerEntity save(String firstname, String lastname) {
        CustomerEntity customerEntity = new CustomerEntity(firstname, lastname, true);
        return repository.save(customerEntity);
    }

    public List<CustomerEntity> findByLastName(String lastname) {
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
    public List<CustomerEntity> findAll() {
        return repository.findAll();
    }


}
