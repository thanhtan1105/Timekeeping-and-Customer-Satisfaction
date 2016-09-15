package com.timelinekeeping.repository;

import com.timelinekeeping.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/1/16.
 */

@Repository
@Component
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {

    List<CustomerEntity> findByLastName(String lastname);



}
