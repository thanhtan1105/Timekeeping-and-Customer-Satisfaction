package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/4/2016.
 */
@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByUsername(String username);

    @Query(value = "SELECT * FROM account a WHERE a.user_code = ?1", nativeQuery = true)
    AccountEntity findByCode(String code);

    @Query(value = "SELECT * FROM account a WHERE a.department_id = ?1 LIMIT ?2, ?3", nativeQuery = true)
    List<AccountEntity> findByDepartment(int departmentId, int start, int top);

}
