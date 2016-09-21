package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.AccountModifyModel;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by HienTQSE60896 on 9/4/2016.
 */

@Entity
@Table(name = "account", schema = "mydb")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Basic
    @NotNull
    @Column(name = "user_code", nullable = false, unique = true)
    private String userCode;

    @Basic
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "active")
    private EStatus active = EStatus.ACTIVE;

    @Basic
    @Column(name = "full_name", length = Integer.MAX_VALUE)
    private String fullname;

    @Basic
    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)

    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FaceEntity> faces;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "manager")
    private DepartmentEntity manager;

    public AccountEntity() {
    }

    public AccountEntity(AccountModifyModel model) {
        if (model != null) {
            this.username = StringUtils.isNotEmpty(model.getUsername()) ? model.getUsername() : this.username;
            this.fullname = StringUtils.isNotEmpty(model.getFullname()) ? model.getFullname() : this.fullname;
            this.password = StringUtils.isNotEmpty(model.getPassword()) ? model.getPassword() : this.password;
            this.active = model.getActive() != null ? EStatus.fromIndex(model.getActive()) : this.active;
        }
    }
    public void update(AccountModifyModel model){
        if (model != null) {
            this.username = StringUtils.isNotEmpty(model.getUsername()) ? model.getUsername() : this.username;
            this.fullname = StringUtils.isNotEmpty(model.getFullname()) ? model.getFullname() : this.fullname;
            this.password = StringUtils.isNotEmpty(model.getPassword()) ? model.getPassword() : this.password;
            this.active = model.getActive() != null ? EStatus.fromIndex(model.getActive()) : this.active;
        }
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
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

    public void setRole(RoleEntity roles) {
        this.role = roles;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity departments) {
        this.department = departments;
    }

    public Set<FaceEntity> getFaces() {
        return faces;
    }

    public void setFaces(Set<FaceEntity> faces) {
        this.faces = faces;
    }

    public DepartmentEntity getManager() {
        return manager;
    }

    public void setManager(DepartmentEntity manager) {
        this.manager = manager;
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