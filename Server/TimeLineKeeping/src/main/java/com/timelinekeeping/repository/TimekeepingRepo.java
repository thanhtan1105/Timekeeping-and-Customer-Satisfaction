package com.timelinekeeping.repository;

import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public interface TimekeepingRepo extends JpaRepository<TimeKeepingEntity, Long> {

    @Query("SELECT t FROM TimeKeepingEntity t WHERE t.account.id = :accountId AND t.timeCheck = current_date ")
    public TimeKeepingEntity findByAccountCheckinCurrent(@Param("accountId") Long accountId);
}
