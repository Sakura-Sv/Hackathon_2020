package com.testdb.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

//@Builder
@Data
@NoArgsConstructor
@TableName(value = "user")
public class User extends BaseUser implements UserDetails {

    /**
     * 用户完整模型
     */

    @TableId(type = IdType.AUTO)
    private long id;

    @Email(message = "Username must be a enable Email")
    private String username;
    private String password;
    private Boolean enable;
    @TableField(exist = false)
    private List<Role> roles;
    private String confirmCode;
    private LocalDateTime createdTime;
    private LocalDate birthday;
    private String sex;
    private String description;
    private String nickname;

    public User(String username, String password, String nickname, List<Role>roles){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
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
