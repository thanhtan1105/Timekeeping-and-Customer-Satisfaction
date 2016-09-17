package com.timelinekeeping.repository;

import com.timelinekeeping.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by HienTQSE60896 on 9/4/2016.
 */
@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT a FROM AccountEntity a WHERE a.username = :username and a.active <>0")
    AccountEntity findByUsername(@Param("username") String username);

    @Query("SELECT a FROM AccountEntity a WHERE a.userCode = :code and a.active <>0")
    AccountEntity findByUsercode(@Param("code") String code);
}
