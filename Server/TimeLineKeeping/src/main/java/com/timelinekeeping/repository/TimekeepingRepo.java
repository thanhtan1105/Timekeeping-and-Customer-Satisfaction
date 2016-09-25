package com.timelinekeeping.repository;

import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;


/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public interface TimekeepingRepo extends JpaRepository<TimeKeepingEntity, Long> {

    @Query(value = "SELECT * FROM time_keeping t, account a WHERE a.id = t.account_id and a.id = :accountId and date(t.time_check) = date(:date) LIMIT 1;", nativeQuery = true)
    public TimeKeepingEntity findByAccountCheckinDate(@Param("accountId") Long accountId,
                                                         @Param("date")Date date);

    @Query("SELECT t FROM TimeKeepingEntity t WHERE t.account.id = :accountId")
    public TimeKeepingEntity findByAccount(@Param("accountId") Long accountId);
}
