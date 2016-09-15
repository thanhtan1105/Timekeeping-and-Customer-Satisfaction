package com.timelinekeeping.repository;

import com.timelinekeeping.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Lep on 9/7/2016.
 */
@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {



}
