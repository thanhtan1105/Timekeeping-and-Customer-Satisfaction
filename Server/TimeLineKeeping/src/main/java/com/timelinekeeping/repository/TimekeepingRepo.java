package com.timelinekeeping.repository;

import com.timelinekeeping.entity.TimeKeepingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public interface TimekeepingRepo extends JpaRepository<TimeKeepingEntity, Long> {

    @Query(value = "SELECT * FROM time_keeping t WHERE t.account_id = :accountId and date(t.time_check) = date(:date) LIMIT 1;", nativeQuery = true)
    public TimeKeepingEntity findByAccountCheckinDate(@Param("accountId") Long accountId,
                                                      @Param("date") Date date);

    @Query("SELECT t FROM TimeKeepingEntity t WHERE t.account.id = :accountId")
    public TimeKeepingEntity findByAccount(@Param("accountId") Long accountId);

    @Query(value = "SELECT t.account_id, count(id) FROM time_keeping t WHERE year(t.time_check) = :year and month(t.time_check) = :month group by t.account_id;", nativeQuery = true)
    public List<Object[]> countEmployeeTime(@Param("year") Integer year,
                                            @Param("month") Integer month);

    @Query(value = "SELECT * FROM time_keeping t WHERE  t.account_id = :accountId AND year(t.time_check) = :year and month(t.time_check) = :month ", nativeQuery = true)
    public List<TimeKeepingEntity> getTimekeepingByAccount(@Param("accountId") Long accountId,
                                                           @Param("year") Integer year,
                                                           @Param("month") Integer month);

}
