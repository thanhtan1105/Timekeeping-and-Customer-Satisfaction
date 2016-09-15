package com.timelinekeeping.repository;

import com.timelinekeeping.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lethanhtan on 9/15/16.
 */
public interface EmotionRepo extends JpaRepository<Emotion, Long> {


}
