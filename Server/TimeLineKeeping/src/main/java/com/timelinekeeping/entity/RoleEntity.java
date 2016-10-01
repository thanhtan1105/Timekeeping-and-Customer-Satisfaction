package com.timelinekeeping.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Lep on 9/7/2016.
 */
@Entity
@Table(name = "role", schema = "mydb")
public class RoleEntity  implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "allow_page", length = 1000)
    private String allowPage;

    @Basic
    @Column(name = "authen_page", length = 1000)
    private String authenPage;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<AccountEntity> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllowPage() {
        return allowPage;
    }

    public void setAllowPage(String allowPage) {
        this.allowPage = allowPage;
    }

    public String getAuthenPage() {
        return authenPage;
    }

    public void setAuthenPage(String authenPage) {
        this.authenPage = authenPage;
    }

    public Set<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
