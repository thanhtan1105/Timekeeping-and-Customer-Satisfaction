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


}
