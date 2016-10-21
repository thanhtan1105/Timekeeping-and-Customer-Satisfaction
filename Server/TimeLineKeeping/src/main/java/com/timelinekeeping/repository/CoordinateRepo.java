package com.timelinekeeping.repository;

import com.timelinekeeping.entity.CoordinateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by HienTQSE60896 on 10/21/2016.
 */
@Repository
@Component
public interface CoordinateRepo extends JpaRepository<CoordinateEntity, Long> {

    @Query("SELECT c FROM CoordinateEntity c WHERE c.type = 1")
    public List<CoordinateEntity> findAllPointRoom();

    @Query("SELECT c FROM CoordinateEntity c WHERE c.type = 7")
    public List<CoordinateEntity> findAllPointBeacon();

    @Query("SELECT a FROM CoordinateEntity a")
    List<CoordinateEntity> findAll();
}
