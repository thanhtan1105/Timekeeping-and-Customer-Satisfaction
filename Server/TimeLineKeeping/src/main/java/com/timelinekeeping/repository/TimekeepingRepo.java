package com.timelinekeeping.repository;

import com.timelinekeeping.entity.TimeKeepingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public interface TimekeepingRepo extends JpaRepository<TimeKeepingEntity, Long> {
}
