package com.service;

import com.entity.Customer;

import java.util.List;

/**
 * Created by lethanhtan on 9/2/16.
 */
public interface CustomerService {

    public Customer save(String firstname, String lastname);

    public List<Customer> findByLastName(String lastname);

    public boolean deleteById(Long id);

    public List<Customer> findAll();
}
