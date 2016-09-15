package com.timelinekeeping.entity;

import com.timelinekeeping.model.Account;

import javax.persistence.*;

/**
 * Created by HienTQSE60896 on 9/4/2016.
 */

@Entity
@Table(name = "account", schema = "mydb")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Integer active;

    @Column(name = "full_name")
    private String fullname;

    @Column(name = "token")
    private String token;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    public AccountEntity() {
    }

    public AccountEntity(Account account) {
        this.username = account.getUsername();
        this.fullname = account.getFullname();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", fullname='" + fullname + '\'' +
                ", token='" + token + '\'' +
                ", role=" + role +
                '}';
    }
}
