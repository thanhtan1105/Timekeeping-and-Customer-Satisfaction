package com.repository;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by HienTQSE60896 on 9/4/2016.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

//    @Query("SELECT * FROM user")
//    public User checkLogin(String username, String password);


}
