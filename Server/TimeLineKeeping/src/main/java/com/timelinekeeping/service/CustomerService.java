package com.timelinekeeping.service;

import com.timelinekeeping.entity.CustomerEntity;

import java.util.List;

/**
 * Created by lethanhtan on 9/2/16.
 */
public interface CustomerService {

    public CustomerEntity save(String firstname, String lastname);

    public List<CustomerEntity> findByLastName(String lastname);

    public boolean deleteById(Long id);

    public List<CustomerEntity> findAll();
}
