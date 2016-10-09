package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.ToDoListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 10/9/16.
 */

@Repository
public interface ToDoListRepo extends JpaRepository<ToDoListEntity, Long> {

    // TO DO LIST
    @Query(value = "SELECT * FROM todo_list a WHERE a.account_id = ?1 AND MONTH(a.time_notify) = ?2 AND YEAR(a.time_notify) = ?3 AND DAY(a.time_notify) = ?4", nativeQuery = true)
    List<ToDoListEntity> findByToDoListOnMonth(@Param("accountId") String accountId,
                                               @Param("month") int month,
                                               @Param("year") int year,
                                               @Param("day") int day);

}
