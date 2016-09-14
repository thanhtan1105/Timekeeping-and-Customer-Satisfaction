package com.timelinekeeping.repository;

import com.timelinekeeping.entity.Department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by lethanhtan on 9/14/16.
 */
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    @Query(value = "SELECT * FROM department d WHERE d.name LIKE ?1", nativeQuery = true)
    List<Department> findByName(String name);

    @Query(value = "SELECT * FROM department d WHERE d.name = ?1", nativeQuery = true)
    List<Department> isExist(String name);

    Page<Department> findAll(Pageable pageable);

}
