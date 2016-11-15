package com.timelinekeeping.repository;

import com.timelinekeeping.entity.ToDoListEntity;
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
    @Query(value = "SELECT td FROM ToDoListEntity td WHERE td.account_created.id = :accountId AND  (td.timeNotify BETWEEN :fromDay AND :toDay ) AND td.active <> 0")
    List<ToDoListEntity> findByToDoListOnMonth(@Param("accountId") Long accountId,
                                               @Param("fromDay") Date fromDay,
                                               @Param("toDay") Date toDay);

    @Query("SELECT  todo FROM ToDoListEntity  todo WHERE todo.id = ?1 and todo.active <> 0")
    ToDoListEntity findOne(Long id);
}
