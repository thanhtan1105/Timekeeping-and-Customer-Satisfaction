package com.timelinekeeping.repository;
import com.timelinekeeping.entity.CoordinateEntity;
import com.timelinekeeping.model.CoordinateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by lethanhtan on 10/20/16.
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

    @Query("SELECT a FROM CoordinateEntity a WHERE a.floor = :floor AND a.type = 3")
    CoordinateEntity findStairsPointUp(@Param("floor") Integer floor);

    @Query("SELECT a FROM CoordinateEntity a WHERE a.floor = :floor AND a.type = 4")
    CoordinateEntity findStairsPointDown(@Param("floor") Integer floor);
}
