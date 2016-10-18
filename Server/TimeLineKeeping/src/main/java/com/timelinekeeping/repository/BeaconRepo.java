package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.BeaconEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lethanhtan on 10/17/16.
 */

@Repository
public interface BeaconRepo extends JpaRepository<BeaconEntity, Long> {


}
