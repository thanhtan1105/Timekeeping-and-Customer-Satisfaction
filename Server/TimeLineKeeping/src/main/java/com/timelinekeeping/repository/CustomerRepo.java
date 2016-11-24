package com.timelinekeeping.repository;

import com.timelinekeeping.entity.BeaconEntity;
import com.timelinekeeping.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lethanhtan on 10/17/16.
 */

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT  c FROM CustomerEntity  c WHERE c.code = :code")
    CustomerEntity findByCode (@Param("code") String code);


}
