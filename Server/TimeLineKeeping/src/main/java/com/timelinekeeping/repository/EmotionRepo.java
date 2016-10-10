package com.timelinekeeping.repository;

import com.timelinekeeping.entity.EmotionCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lethanhtan on 9/15/16.
 */
public interface EmotionRepo extends JpaRepository<EmotionCustomerEntity, Long> {

    /**
     * @author TrungNN
     * Using for getting 1st customer emotion
     */
    @Query(value = "SELECT e FROM EmotionCustomerEntity e INNER JOIN e.customerService c " +
            "WHERE c.id = :customerId")
    public List<EmotionCustomerEntity> findByCustomerId(@Param("customerId") Long customerId);

    @Query(value = "select * from mydb.emotion c where customer_id = 2 order by c.create_time desc limit 1;", nativeQuery = true)
    public EmotionCustomerEntity findByCustomerIdLeast(@Param("customerId") Long customerId);
}
