package com.timelinekeeping.repository;

import com.timelinekeeping.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/1/16.
 */

@Repository
@Component
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastname);

    @Override
    @Modifying
    @Query(value = "UPDATE customer c SET c.active = false WHERE c.id= ?1", nativeQuery = true)
    void delete(Long aLong);

}
