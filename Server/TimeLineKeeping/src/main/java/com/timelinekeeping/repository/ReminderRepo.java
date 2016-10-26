package com.timelinekeeping.repository;

import com.timelinekeeping.entity.ReminderMessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
@Repository
public interface ReminderRepo extends JpaRepository<ReminderMessageEntity, Long> {


    @Query("SELECT re FROM ReminderMessageEntity re INNER JOIN re.manager a WHERE a.id = :manager_id AND re.active <> 0 ORDER BY re.createDate desc ")
    Page<ReminderMessageEntity> list(@Param("manager_id") Long managerId, Pageable pageable);

    @Query("SELECT re FROM ReminderMessageEntity re INNER JOIN re.manager a " +
            "WHERE a.id = :manager_id AND re.title like CONCAT('%', :title, '%') AND re.active <> 0 ORDER BY re.createDate desc")
    Page<ReminderMessageEntity> search(@Param("title") String title,
                                       @Param("manager_id") Long managerId, Pageable pageable);

}
