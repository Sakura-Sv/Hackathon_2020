package com.testdb.demo.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    @TableId(type = IdType.AUTO)
    private long id;
    private String username;
    private long score;

    public Score(String username, long score){
        this.username = username;
        this.score = score;
    }

}
