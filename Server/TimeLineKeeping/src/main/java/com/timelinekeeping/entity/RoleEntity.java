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
    @Column(name = "redirect")
    private String redirect;

    @Basic
    @Column(name = "allow_page")
    public String allowPage;



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

    public Set<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountEntity> accounts) {
        this.accounts = accounts;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getAllowPage() {
        return allowPage;
    }

    public void setAllowPage(String allowUser) {
        this.allowPage = allowUser;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
