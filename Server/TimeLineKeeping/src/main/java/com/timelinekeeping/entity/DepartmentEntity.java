package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.ETrainStatus;
import com.timelinekeeping.model.DepartmentModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by lethanhtan on 9/14/16.
 */

@Entity
@Table(name = "department", schema = "mydb")
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Basic
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Basic
    @Column(name = "active", nullable = false)
    private EStatus active = EStatus.ACTIVE;

    @Basic
    @Column(name = "status", nullable = false)
    private ETrainStatus status = ETrainStatus.NOT_STARTED;


    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AccountEntity> accountEntitySet;

    public DepartmentEntity() {
    }

    public DepartmentEntity(DepartmentModel model) {
        if (model != null) {
            this.code = model.getCode();
            this.name = model.getName();
            this.description = model.getDescription();
            this.active = model.getActive();
            this.status = model.getStatus();
        }
    }

    public DepartmentEntity(String code, String name, String description, EStatus active) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public void update(DepartmentModel model) {
        if (model != null) {
            this.name = model.getName();
            this.description = model.getDescription();
        }
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public Set<AccountEntity> getAccountEntitySet() {
        return accountEntitySet;
    }

    public void setAccountEntitySet(Set<AccountEntity> accountEntitySet) {
        this.accountEntitySet = accountEntitySet;
    }

    public ETrainStatus getStatus() {
        return status;
    }

    public void setStatus(ETrainStatus status) {
        this.status = status;
    }
}
