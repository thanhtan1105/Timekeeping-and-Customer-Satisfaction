package com.timelinekeeping.entity;

import com.timelinekeeping.model.AccountModel;
import javax.persistence.*;
import java.util.List;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.model.AbstractModel;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by HienTQSE60896 on 9/4/2016.
 */

@Entity
@Table(name = "account", schema = "mydb")
public class AccountEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @Basic
    @NotNull
    @Column(name = "user_code")
    private String userCode;

    @Basic
    @NotNull
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "active")
    private EStatus active = EStatus.ACTIVE;

    @Basic
    @Column(name = "full_name")
    private String fullname;

    @Basic
    @Column(name = "token")
    private String token;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity departments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountEntity", fetch = FetchType.EAGER)
    private List<FaceEntity> faces;


    public AccountEntity() {
    }

    public AccountEntity(AccountModel accountModel) {
        this.username = accountModel.getUsername();
        this.fullname = accountModel.getFullname();
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

    public RoleEntity getRoles() {
        return roles;
    }

    public void setRoles(RoleEntity roles) {
        this.roles = roles;
    }

    public DepartmentEntity getDepartments() {
        return departments;
    }

    public void setDepartments(DepartmentEntity departments) {
        this.departments = departments;
    }

    @Override
    public <T extends AbstractModel> void fromModel(T modelGeneric) {
        AccountModel model = (AccountModel) modelGeneric;
        this.username = model.getUsername();
        this.fullname = model.getFullname();
    }
}
