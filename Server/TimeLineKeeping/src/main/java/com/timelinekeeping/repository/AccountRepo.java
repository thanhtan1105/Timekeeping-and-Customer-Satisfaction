package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by HienTQSE60896 on 9/4/2016.
 */
@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT count(a.id) FROM AccountEntity a WHERE a.username = :username and a.active <>0")
    Integer checkExistUsername(@Param("username") String username);

    @Query("SELECT a FROM AccountEntity a WHERE a.username = ?1 and a.active <>0")
    AccountEntity findByUsername(String username);

    @Query("SELECT a FROM AccountEntity a WHERE a.userCode = ?1 and a.active <>0")
    AccountEntity findByUsercode(String code);

    @Query("SELECT a FROM AccountEntity a WHERE a.active <>0")
    List<AccountEntity> findAll();

    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.active <> 0")
    Page<AccountEntity> findByDepartment(@Param("department_id") Long departmentId, Pageable pageable);
}
