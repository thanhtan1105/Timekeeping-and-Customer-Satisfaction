package com.timelinekeeping.repository;

import com.timelinekeeping.entity.TimeKeepingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public interface TimekeepingRepo extends JpaRepository<TimeKeepingEntity, Long> {

    @Query(value = "SELECT t FROM TimeKeepingEntity t WHERE t.account.id = :accountId and (t.timeCheck between :fromDay and :toDay)")
    public Page<TimeKeepingEntity> findByAccountCheckinDate(@Param("accountId") Long accountId,
                                                            @Param("fromDay") Date fromDay,
                                                            @Param("toDay") Date toDay,
                                                            Pageable pageable);

    @Query("SELECT t FROM TimeKeepingEntity t INNER JOIN t.account a WHERE a.id = :accountId")
    public List<TimeKeepingEntity> findByAccount(@Param("accountId") Long accountId);

    @Query(value = "SELECT t FROM TimeKeepingEntity t WHERE (t.timeCheck between :fromMonth and :toMonth)")
    public List<TimeKeepingEntity> countEmployeeTime( @Param("fromMonth") Date fromMonth,
                                                      @Param("toMonth") Date toMonth);

    @Query(value = "SELECT t FROM TimeKeepingEntity t WHERE  t.account.id = :accountId AND (t.timeCheck between :fromMonth and :toMonth)")
    public List<TimeKeepingEntity> getTimekeepingByAccount(@Param("accountId") Long accountId,
                                                           @Param("fromMonth") Date fromMonth,
                                                           @Param("toMonth") Date toMonth);

}

