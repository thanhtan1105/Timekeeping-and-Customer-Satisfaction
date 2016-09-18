package com.timelinekeeping.repository;

import com.timelinekeeping.entity.EmotionCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lethanhtan on 9/15/16.
 */
public interface EmotionRepo extends JpaRepository<EmotionCustomerEntity, Long> {


}
