package com.timelinekeeping._config;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.repository.RoleRepo;
import com.timelinekeeping.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lep on 9/7/2016.
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntity = accountRepo.findByUsername(username);
        String roleName = roleRepo.getOne(accountEntity.getRoles()).getName();
        List<GrantedAuthority> authorities = buildUserAuthority(roleName);
        return buildUserForAuthentication(accountEntity, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(AccountEntity accountEntity, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(accountEntity.getUsername(), accountEntity.getPassword(),
                accountEntity.getActive() == 1, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String roleName) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        setAuths.add(new SimpleGrantedAuthority(roleName));

        return new ArrayList<GrantedAuthority>(setAuths);
    }
}
