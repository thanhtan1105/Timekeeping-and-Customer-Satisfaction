package com.timelinekeeping._config;

import com.timelinekeeping.entity.User;
import com.timelinekeeping.repository.RoleRepo;
import com.timelinekeeping.repository.UserRepo;
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
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        String roleName = roleRepo.getOne(user.getRole()).getName();
        List<GrantedAuthority> authorities = buildUserAuthority(roleName);
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getActive() == 1, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String roleName) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        setAuths.add(new SimpleGrantedAuthority(roleName));

        return new ArrayList<GrantedAuthority>(setAuths);
    }
}
