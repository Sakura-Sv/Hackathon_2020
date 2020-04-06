package com.testdb.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.testdb.demo.entity.letter.BaseLetter;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.service.BaseLetterService;
import com.testdb.demo.service.LetterService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
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
    public Result<Page<BaseLetter>> getLetterList(Principal principal, @RequestParam("index") int index){
        return Result.success(bls.getBaseLetterList(principal.getName(), index));
    }

    @GetMapping("/others")
    public Result<Page<BaseLetter>> getOthersLetterList(@RequestParam("index") int index,
                                                        @RequestParam("username") String username){
        return Result.success(bls.getBaseLetterList(username, index));
    }

}
