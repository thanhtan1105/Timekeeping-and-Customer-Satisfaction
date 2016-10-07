package com.timelinekeeping.repository;

import com.timelinekeeping.entity.QuantityEmotionEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lep on 9/7/2016.
 */
@Repository
public interface QuantityRepo extends JpaRepository<QuantityEmotionEnity, Long> {

    @Query("SELECT q.name FROM QuantityEmotionEnity  q WHERE (?1 BETWEEN q.fromValue and q.toValue)")
    public List<String> findQuantity(Double value);


}
