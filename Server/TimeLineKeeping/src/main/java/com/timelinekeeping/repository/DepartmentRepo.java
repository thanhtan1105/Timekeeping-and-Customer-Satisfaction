package com.timelinekeeping.repository;

import com.timelinekeeping.entity.DepartmentEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lethanhtan on 9/14/16.
 */
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Long> {

    @Query(value = "SELECT * FROM department d WHERE d.name LIKE ?1", nativeQuery = true)
    List<DepartmentEntity> findByName(String name);

    @Query("SELECT d FROM DepartmentEntity d WHERE d.name = :code")
    List<DepartmentEntity> isExist(@Param("code") String code);

    Page<DepartmentEntity> findAll(Pageable pageable);

    @Query("SELECT d FROM DepartmentEntity d WHERE (:code = null OR d.code = :code) " +
            "AND (:name = null OR d.name like CONCAT('%', :name, '%') AND d.active <> 0)")
    Page<DepartmentEntity> search(@Param("code") String code,
                                  @Param("name") String name,
                                  Pageable pageable);



}
