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

    @Query("select f from FaceEntity f inner join f.accountEntity a where a.id = :account_id")
    public List<FaceEntity> findByAccount(@Param("account_id") Long accountiD);

    @Query("SELECT f FROM FaceEntity f WHERE f.persistedFaceId = :persisted_face_id")
    public FaceEntity findByPersistentId(@Param("persisted_face_id") String persistedFaceId);
}
