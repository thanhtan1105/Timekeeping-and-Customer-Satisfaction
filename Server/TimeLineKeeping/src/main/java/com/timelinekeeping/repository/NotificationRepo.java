package com.timelinekeeping.repository;

import com.timelinekeeping.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by HienTQSE60896 on 9/20/2016.
 */
@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {
}
