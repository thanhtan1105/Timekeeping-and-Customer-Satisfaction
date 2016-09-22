package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
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

    @Query("SELECT d FROM DepartmentEntity d WHERE (d.name LIKE :department) AND (d.active <> 0)")
    List<DepartmentEntity> findByDepartmentName(@Param("department") String name);

    @Query("SELECT COUNT (d.id) FROM DepartmentEntity d WHERE d.code = :code")
    Integer isExist(@Param("code") String code);

    @Query("SELECT d FROM DepartmentEntity d")
    Page<DepartmentEntity> findAll(Pageable pageable);

    @Query("SELECT d FROM DepartmentEntity d WHERE (:code != NULL  OR d.code = :code) " +
            "AND (:name != NULL OR d.name like CONCAT('%', :name, '%') AND d.active <> 0)")
    Page<DepartmentEntity> search(@Param("code") String code,
                                  @Param("name") String name,
                                  Pageable pageable);

    @Query("SELECT d.accountEntitySet FROM DepartmentEntity d WHERE d.id = :department_id AND d.active <> 0")
    List<AccountEntity> findByDepartment(@Param("department_id") Long departmentId, Pageable pageable);



}
