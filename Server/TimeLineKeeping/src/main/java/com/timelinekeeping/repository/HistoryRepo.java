package com.timelinekeeping.repository;

import com.timelinekeeping.entity.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by HienTQSE60896 on 11/8/2016.
 */
@Repository
public interface HistoryRepo extends JpaRepository<HistoryEntity, Long>{

    @Query("SELECT h FROM HistoryEntity  h WHERE h.type = 0 order by h.time desc")
    Page<HistoryEntity> findBySynchronize(Pageable pageable);
}
