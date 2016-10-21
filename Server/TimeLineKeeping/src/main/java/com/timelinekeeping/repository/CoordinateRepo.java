package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CoordinateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lethanhtan on 10/20/16.
 */
public interface CoordinateRepo extends JpaRepository<CoordinateEntity, Long> {

    @Query("SELECT a FROM CoordinateEntity a")
    List<CoordinateEntity> findAll();
}
