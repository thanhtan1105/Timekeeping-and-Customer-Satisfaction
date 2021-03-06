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

    @Query("SELECT count(a.id) FROM AccountEntity a WHERE a.email = :email and a.active <>0")
    Integer checkExistEmail(@Param("email") String email);

    @Query("SELECT a FROM AccountEntity a WHERE a.username = ?1 and a.active <>0")
    AccountEntity findByUsername(String username);

    @Query("SELECT a FROM AccountEntity a WHERE a.userCode = ?1 and a.active <>0")
    AccountEntity findByUserCode(String code);

    @Query("SELECT a FROM AccountEntity a WHERE a.active <>0")
    List<AccountEntity> findAll();


    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.role.id = 2 AND a.active <> 0")
    Page<AccountEntity> findManagerByDepartment(@Param("department_id") Long departmentId,
                                                Pageable pageable);

    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.active <> 0")
    Page<AccountEntity> findByDepartmentPaging(@Param("department_id") Long departmentId, Pageable pageable);

    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.active <> 0")
    List<AccountEntity> findByDepartment(@Param("department_id") Long departmentId);

    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d WHERE d.id = :department_id AND a.role.id = 3 AND a.active <> 0")
    List<AccountEntity> findEmployeeByDepartment(@Param("department_id") Long departmentId);


    //    @Query("SELECT a FROM AccountEntity a INNER JOIN a.department d INNER JOIN a.role r " +
//            "WHERE d.id = :department_id AND r.id <> :role_id AND a.active <> 0")
//    List<AccountEntity> findByDepartmentAndRole(@Param("department_id") Long departmentId,
//                                                @Param("role_id") Long roleId);
    @Query("SELECT a FROM  AccountEntity a inner join a.department d WHERE d.id = :departmentId and a.role.id = 3")
    List<AccountEntity> findEmployeeByDepartmentNoActive(@Param("departmentId") Long departmentId);


    @Query("SELECT a FROM AccountEntity a WHERE a.keyOneSignal = :keyOne and a.active <>0")
    List<AccountEntity> findByOneSignal(@Param("keyOne") String key);

//    @Query("SELECT a FROM AccountEntity a WHERE a.manager.id = :managerId AND a.role.id = 3 AND a.active <> 0 ")
//    List<AccountEntity> findByManager(@Param("managerId") Long managerId);


    @Query("SELECT a FROM AccountEntity a WHERE a.username = :username and a.password = :password and a.active <>0")
    AccountEntity findByUserNameAndPassword(@Param("username") String username,
                                            @Param("password") String password);

//    @Query("SELECT a FROM  AccountEntity a WHERE a.manager.id = :managerId and a.role.id = 3")
//    List<AccountEntity> findByManagerNoActive(@Param("managerId") Long managerId);

    @Query("SELECT a FROM  AccountEntity a INNER JOIN a.role r WHERE  r.id = 2 and a.active <> 0")
    List<AccountEntity> listAllManger();

}
