package com.testdb.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Avatar {

    @TableId(type = IdType.AUTO)
    private String id;
    private String username;
    private String url;

    public Avatar(String username){
        this.username = username;
    }
}
