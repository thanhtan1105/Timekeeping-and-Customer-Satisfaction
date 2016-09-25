package com.timelinekeeping.repository;

import com.timelinekeeping.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/20/2016.
 */
@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {


    @Query(value = "SELECT n.* FROM notification n, reminder_message rm WHERE n.reminder_message_id = rm.id and n.account_id = ?1 and date(rm.time) = curdate() ", nativeQuery = true)
    public List<NotificationEntity> findByAccountReceiveByDate(Long accountid);



}
