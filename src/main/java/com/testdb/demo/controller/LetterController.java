package com.testdb.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.BaseLetter;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.service.BaseLetterService;
import com.testdb.demo.service.LetterService;
import com.testdb.demo.service.UserService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.QiniuUtil;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value="/api/letter")
@AjaxResponseBody
public class LetterController {

    @Autowired
    LetterService ls;

    @Autowired
    UserService us;

    @Autowired
    BaseLetterService bls;

    @GetMapping
    public Result<Letter> getLetter(@RequestParam("id") String id){
        return Result.success(ls.getLetter(id));
    }

    @PostMapping
    public Result<Void> postLetter(Principal principal, @RequestBody Letter letter){
        ls.postLetter(principal.getName(), letter);
        return Result.success();
    }

    @GetMapping("/preview")
    public Result<Page<BaseLetter>> getLetterList(Principal principal,
                                                  @RequestParam(value = "index", defaultValue = "1") int index){
        return Result.success(bls.getBaseLetterList(principal.getName(), index));
    }

    @GetMapping("/others")
    public Result<Page<BaseLetter>> getOthersLetterList(@RequestParam(value = "index", defaultValue = "1") int index,
                                                        @RequestParam("username") String username){
        if(us.checkInvalidUser(username)){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在此用户！"));
        }
        return Result.success(bls.getBaseLetterList(username, index));
    }

    @GetMapping("/annex")
    public Result<String> getAnnexUploadToken(){
        return Result.success(QiniuUtil.getRandomKeyToken());
    }

}
