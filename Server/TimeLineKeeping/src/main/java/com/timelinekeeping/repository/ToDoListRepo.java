package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.ToDoListEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by lethanhtan on 10/9/16.
 */

@Repository
public interface ToDoListRepo extends JpaRepository<ToDoListEntity, Long> {

    // TO DO LIST
    @Query(value = "SELECT * FROM todo_list a WHERE a.account_id = :accountId AND  date(a.time_notify) = date(:date) AND a.active <> 0", nativeQuery = true)
    List<ToDoListEntity> findByToDoListOnMonth(@Param("accountId") String accountId,
                                               @Param("date") Date month);

    @Query("SELECT  todo FROM ToDoListEntity  todo WHERE todo.id = ?1 and todo.active <> 0")
    ToDoListEntity findOne(Long id);
}
