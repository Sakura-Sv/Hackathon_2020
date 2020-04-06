package com.testdb.demo.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @TableId
    private int id;
    private String roleName;
    private int roleValue;

    public Role(String roleName, int roleValue){
        this.roleName = roleName;
        this.roleValue = roleValue;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
