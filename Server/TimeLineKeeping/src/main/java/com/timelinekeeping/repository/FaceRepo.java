package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lethanhtan on 9/19/16.
 */

@Repository
public interface FaceRepo extends JpaRepository<FaceEntity, Long> {

    @Query("select f from FaceEntity f")
    public List<FaceEntity> findByAccount(@Param("account_id") Long accountiD);
    
}
