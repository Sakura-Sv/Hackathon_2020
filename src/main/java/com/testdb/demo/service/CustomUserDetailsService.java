package com.testdb.demo.service;

import com.testdb.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * 仅用于认证，其他用户业务写在UserService
     * @Autuor: 刘镇
     */

    @Autowired
    UserMapper um;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = null;

        try {
             user = um.selectUserAndRoles(username);
        } catch (Exception e) {
            throw e;
        }
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

}
