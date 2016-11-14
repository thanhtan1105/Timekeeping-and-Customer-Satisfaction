package com.timelinekeeping.repository;

import com.timelinekeeping.entity.ConfigurationEntity;
import com.timelinekeeping.entity.ConnectionPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lethanhtan on 10/17/16.
 */

@Repository
public interface ConfigurationRepo extends JpaRepository<ConfigurationEntity, Long> {

}
