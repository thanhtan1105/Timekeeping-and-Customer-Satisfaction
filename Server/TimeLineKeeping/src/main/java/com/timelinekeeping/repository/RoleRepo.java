package com.timelinekeeping.repository;

import com.timelinekeeping.entity.Role;
import com.timelinekeeping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lep on 9/7/2016.
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findById(Long id);
}
