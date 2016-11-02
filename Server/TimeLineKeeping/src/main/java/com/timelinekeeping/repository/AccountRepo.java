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
    AccountEntity findByUserCode(String code);

//    @Query(value = "SELECT * FROM account a WHERE a.id = ?1 and a.active <>0", nativeQuery = true)
//    AccountEntity findById(Long id);

    @Query("SELECT a FROM AccountEntity a WHERE a.active <>0")
    List<AccountEntity> findAll();

    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.active <> 0")
    Page<AccountEntity> findByDepartmentPaging(@Param("department_id") Long departmentId, Pageable pageable);

    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.active <> 0")
    List<AccountEntity> findByDepartment(@Param("department_id") Long departmentId);

//    @Query("UPDATE account SET token = :tokenID WHERE id = :accountID", nativeQuery = true)
//    int updateMobileTokenID(@Param("accountID") Long accountID,
//                            @Param("tokenID") String tokenID);

    @Query("SELECT a FROM AccountEntity a WHERE a.keyOneSignal = :keyOne and a.active <>0")
    List<AccountEntity> findByOneSignal(@Param("keyOne") String key);

    @Query("SELECT a FROM AccountEntity a WHERE a.manager.id = :managerId AND a.active <> 0")
    List<AccountEntity> findByManager(@Param("managerId") Long managerId);


    @Query("SELECT a FROM AccountEntity a WHERE a.username = :username and a.password = :password and a.active <>0")
    AccountEntity findByUserNameAndPassword(@Param("username") String username,
                                            @Param("password") String password);

    @Query("SELECT a FROM  AccountEntity a WHERE a.manager.id = :managerId")
    List<AccountEntity> findByManagerNoActive(@Param("managerId") Long managerId);

    @Query("SELECT a FROM  AccountEntity a INNER JOIN a.role r WHERE  r.id = 2 and a.active <> 0")
    List<AccountEntity> listAllManger();
}
