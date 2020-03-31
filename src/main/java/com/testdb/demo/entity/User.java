package com.testdb.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

//@Builder
@Data
@NoArgsConstructor
@TableName(value = "user")
public class User implements UserDetails {

    @TableId(type = IdType.AUTO)
    private long id;

    @Email(message = "Username must be a enable Email")
    private String username;
    private String password;
    private Boolean enable;
    @TableField(exist = false)
    private List<Role> roles;
    private String confirmCode;

    public User(String username, String password, List<Role>roles){
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

}
