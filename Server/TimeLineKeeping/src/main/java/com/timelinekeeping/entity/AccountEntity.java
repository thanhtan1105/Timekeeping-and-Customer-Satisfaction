package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.AccountModifyModel;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * Created by HienTQSE60896 on 9/4/2016.
 */

@Entity
@Table(name = "account")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Basic
    @Column(name = "user_code", nullable = false, unique = true)
    private String userCode;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "full_name", length = 1000, nullable = false)
    private String fullName;

    @Basic
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Basic
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Basic
    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Basic
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Basic
    @Column(name = "note", length = Integer.MAX_VALUE)
    private String note;

    @Basic
    @Column(name = "active", nullable = false)
    private EStatus active = EStatus.ACTIVE;


    @Basic
    @Column(name = "token", length = 100)
    private String token;

    @Basic
    @Column(name = "key_one_signal")
    private String keyOneSignal;

    @Basic
    @Column(name = "time_deactive")
    private Timestamp timeDeactive;

    @Basic
    @Column(name = "time_create", nullable = false)
    private Timestamp timeCreate = new Timestamp(new Date().getTime());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private AccountEntity manager;

    public AccountEntity() {

    }

    public AccountEntity(AccountModifyModel model) {
        if (model != null) {
            this.username = StringUtils.isNotEmpty(model.getUsername()) ? model.getUsername() : this.username;
            this.fullName = StringUtils.isNotEmpty(model.getFullName()) ? model.getFullName() : this.fullName;
            this.phone = model.getPhone();
            this.email = model.getEmail();
            this.address = model.getAddress();
            this.gender = model.getGender();
            this.note = model.getNote();
        }
    }

    public void update(AccountModifyModel model) {
        if (model != null) {
            this.username = StringUtils.isNotEmpty(model.getUsername()) ? model.getUsername() : this.username;
            this.fullName = StringUtils.isNotEmpty(model.getFullName()) ? model.getFullName() : this.fullName;
            this.phone = StringUtils.isNotEmpty(model.getPhone()) ? model.getPhone() : this.phone;
            this.email = StringUtils.isNotEmpty(model.getEmail()) ? model.getEmail() : this.email;
            this.address = StringUtils.isNotEmpty(model.getAddress()) ? model.getAddress() : this.address;
            this.gender = model.getGender() != null ? model.getGender() : this.gender;
            this.note = StringUtils.isNotEmpty(model.getNote()) ? model.getNote() : this.note;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Timestamp getTimeDeactive() {
        return timeDeactive;
    }

    public void setTimeDeactive(Long timeDeactive) {
        if (timeDeactive != null) {
            this.timeDeactive = new Timestamp(timeDeactive);
        } else {
            this.timeDeactive = null;
        }
    }

    public Timestamp getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Long timeCreate) {
        this.timeCreate = new Timestamp(timeCreate);
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
//
//    public Set<FaceEntity> getFaces() {
//        return faces;
//    }
//
//    public void setFaces(Set<FaceEntity> faces) {
//        this.faces = faces;
//    }

    public AccountEntity getManager() {
        return manager;
    }

    public void setManager(AccountEntity manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String addition) {
        this.note = addition;
    }

    public String getKeyOneSignal() {
        return keyOneSignal;
    }

    public void setKeyOneSignal(String keyOneSignal) {
        this.keyOneSignal = keyOneSignal;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", fullName='" + fullName + '\'' +
                ", token='" + token + '\'' +
                ", role=" + role +
                '}';
    }
}