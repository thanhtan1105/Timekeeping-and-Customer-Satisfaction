package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CustomerServiceEntity;
import com.timelinekeeping.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Repository
public interface CustomerServiceRepo extends JpaRepository<CustomerServiceEntity, Long> {

    @Query("SELECT c FROM CustomerServiceEntity c WHERE c.CustomerCode = :customerCode")
    public CustomerServiceEntity findByCustomerCode(@Param("customerCode") String customerCode);

    @Query(value = "SELECT * FROM customer_service c WHERE c.create_by = ?1 AND c.status = 0", nativeQuery = true)
    public CustomerServiceEntity getLastCustomerById(@Param("create_by") Long employeeId);

}