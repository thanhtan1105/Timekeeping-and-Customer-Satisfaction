package com.timelinekeeping.repository;

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


}